def precomp file, pA, pB, pP, pG
  # .subckt precomp pA pB pP pG
  # Xprecompand pA pB pG and2
  # Xprecompxor pA pB pP xor2
  # .ends
  file << "#{pG}:= AND(#{pA}, #{pB}); \n"
  file << "#{pP}:= ODD(#{pA}, #{pB}); \n"
end

def black file, gi, pi, gj, pj, g, p
  # .subckt black Gi Pi Gj Pj G P
  # Xinv1 Gi invGi inverter
  # XBnand1 Pi Gj PinandGj nand2
  # XBnand2 invGi PinandGj G nand2
  # XBand Pi Pj P and2
  # .ends
  invGi = "NOT(#{gi})"
  pinandGj = "NOT(AND(#{pi}, #{gj}))"
  file << "#{g}:= NOT(AND(#{pinandGj}, #{invGi})); \n"
  file << "#{p}:= AND(#{pi}, #{pj}); \n"
end

def grey file, gi, pi, gj, g
  # .subckt grey Gi Pi Gj G
  # Xinv1 Gi invGi inverter
  # XGnand1 Pi Gj PinandGj nand2
  # XGnand2 invGi PinandGj G nand2
  # .ends
  invGi = "NOT(#{gi})"
  pinandGj = "NOT(AND(#{pi}, #{gj}))"
  file << "#{g}:= NOT(AND(#{pinandGj}, #{invGi})); \n"
end

def merge file, x, y
  file << "#{y}:= #{x}; \n"
end


open('bkAdder.bc','w') do |file|
  file << "BC1.1\n"
  32.times do |t|
    file << "Bi#{t}:= NOT(B#{t}); \n"
    file << "XB#{t}:= OR( AND(NOT(ALUFN),B#{t}), AND(ALUFN, Bi#{t}) ); \n"
    precomp(file, "A#{t}", "XB#{t}", "P#{t+1}", "G#{t+1}")
  end

  merge(file, "ALUFN", "S9G0")
  file << "P0 := F; \n"

  # Stage1
  grey(file, "G1", "P1", "S9G0", "S1G1")
  (3..31).step(2).each do |t|
    black(file, "G#{t}", "P#{t}", "G#{t-1}", "P#{t-1}", "S1G#{t}", "S1P#{t}")
  end

  # Stage 2
  grey(file, "S1G3", "S1P3", "S1G1", "S2G3")
  (7..31).step(4).each do |t|
    black(file, "S1G#{t}", "S1P#{t}", "S1G#{t-2}", "S1P#{t-2}", "S2G#{t}", "S2P#{t}")
  end

  # Stage 3
  grey(file, "S2G7", "S2P7", "S2G3", "S3G7")
  # XStage3bit15 S2G[15] S2P[15] S2G[11] S2P[11] S3G[15] S3P[15] black
  # XStage3bit23 S2G[23] S2P[23] S2G[19] S2P[19] S3G[23] S3P[23] black
  # XStage3bit31 S2G[31] S2P[31] S2G[27] S2P[27] S3G[31] S3P[31] black
  [15, 23, 31].each do |t|
    black(file, "S2G#{t}", "S2P#{t}", "S2G#{t-4}", "S2P#{t-4}", "S3G#{t}", "S3P#{t}")
  end

  # Stage 4
  grey(file, "S3G15", "S3P15", "S3G7", "S4G15")
  black(file, "S3G31", "S3P31", "S3G23", "S3P23", "S4G31", "S4P31")

  # Stage 5
  grey(file, "S4G31", "S4P31", "S4G15", "S5G31")

  # Stage 6
  grey(file, "S3G23", "S3P23", "S4G15", "S6G23")

  # Stage 7
  temp = [3, 4, 6]
  [11, 19, 27].each_with_index do |t, i|
    grey(file, "S2G#{t}", "S2P#{t}", "S#{temp[i]}G#{t-4}", "S7G#{t}")
  end

  # Stage 8
  grey(file, "S1G5", "S1P5", "S2G3", "S8G5")
  grey(file, "S1G9", "S1P9", "S3G7", "S8G9")
  grey(file, "S1G13", "S1P13", "S7G11", "S8G13")
  grey(file, "S1G17", "S1P17", "S4G15", "S8G17")
  grey(file, "S1G21", "S1P21", "S7G19", "S8G21")
  grey(file, "S1G25", "S1P25", "S6G23", "S8G25")
  grey(file, "S1G29", "S1P29", "S7G27", "S8G29")

  # STAGE 9
  temp = [1,2,8,3,
          8,7,8,4,
          8,7,8,6,
          8,7,8]
  (2..30).step(2).each_with_index do |t, i|
    grey(file, "G#{t}", "P#{t}", "S#{temp[i]}G#{t-1}", "S9G#{t}")
  end

  temp = [1,2,8,3,8,
          7,8,4,8,7,
          8,6,8,7,8,
          5]

  (1..31).step(2).each_with_index do |t, i|
    merge(file, "S#{temp[i]}G#{t}", "S9G#{t}")
  end
  grey(file, "G32", "P32", "S5G31", "S9G32")

  32.times do |t|
    file << "S#{t}:= ODD(S9G#{t}, P#{t+1}); \n"
  end

  # Z
  # zorcascade
  8.times do |t|
    file << "w#{t} := NOT( OR( S#{t}, S#{t+8}, S#{t+16}, S#{t+24}) ); \n"
  end

  # 4andcascade
  2.times do |t|
    file << "int#{t} := NOT( AND( w#{t}, w#{t+2}, w#{t+4}, w#{t+6}) ); \n"
  end

  file << "Z := NOT ( OR(int1, int0) ); \n"

  # V
  file << "ns31 := NOT(S31); \n"
  file << "na31 := NOT(A31); \n"
  file << "nxb31 := NOT(XB31); \n"
  file << "vp1 := NOT( AND(ns31, XB31, A31) ); \n"
  file << "vp2 := NOT( AND(S31, nxb31, na31) ); \n"
  file << "V := NOT( AND(vp1, vp2)); \n"

  merge(file, "S31", "N")

end