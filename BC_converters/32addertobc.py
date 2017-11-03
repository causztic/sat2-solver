adder = open('32Adder.bc','w');
adder.write('BC1.1\n')
adder.write("XB_0:=ODD(ALUFN, B0);\n")
adder.write('s0:= ODD(A0, XB_0, ALUFN);\n')
adder.write('CO_0:=OR( AND(ODD(A0, XB_0), ALUFN), AND(A0, XB_0));\n')

for x in range(1,32):
    XB_x = "XB_{0}:= ODD(ALUFN, B{0});\n".format(x)
    CO_x = 'CO_{0}:=OR( AND(ODD(A{0}, XB_{0}), CO_{1}), AND(A{0}, XB_{0}));\n'.format(x, x-1)
    S_x  = 's{0}:=ODD(A{0}, XB_{0}, CO_{1});\n'.format(x, x-1)
    adder.write(XB_x + CO_x + S_x)  

adder.close()
print("done")