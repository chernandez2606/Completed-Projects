#Carlo Hernandez	Math 4242	Prof: Lesnick 
execfile("matrix_algebra.py")

C = matrixInput()
b = vectorInput(len(C))

mPrint(C)
print toString(b),'\n'

LUSolved = []
InvSolved = []
precRange = 20
for i in range(precRange):
	t = i + 1
	try:
		my_LU = LUSolve(C,b,t)
		my_Inv = InvSolve(C,b,t)
		LUSolved = LUSolved + [ my_LU ]
		InvSolved = InvSolved + [ my_Inv ]
	except Exception:
		print Exception

prec = precRange - len(LUSolved) + 1
for i in range(len(LUSolved)):
	print i+prec, '\t', toString(LUSolved[i]), 	\
		'\t\t\t', toString(InvSolved[i])
print ''


		
	
