#Carlo Hernandez	Math 4242	Prof: Lesnick 
execfile("matrix_algebra.py")
import random
row = int(random.randint(2,10))
precision = int(random.randint(1,10))

def floatRand():
	return random.random()*math.pow(10, random.randint(0,5))*math.pow(-1,random.randint(0,1))

#only square matrices because InvSolve only takes n x n
C = [[floatRand() for i in range(row)] for i in range(row)]
b = [floatRand() for i in range(row)]
C = roundsd(C,precision)
b = roundsd(b,precision)

mPrint(C)
print toString(b)
print precision,'\n'

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
		pass

prec = precRange - len(LUSolved) + 1
for i in range(len(LUSolved)):
	print i+prec, '\t', toString(LUSolved[i]), 	\
		'\t\t\t', toString(InvSolved[i])
print ''


		
	

