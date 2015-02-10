import math
import random

execfile("matrix_algebra.py")
def floatRand():
	return random.random()*math.pow(10, random.randint(0,5))*math.pow(-1,random.randint(0,1))

def norm(b):
	my_sum = 0
	for x in b:
		my_sum = my_sum + x*x
	return math.sqrt(my_sum)

normLU = 0
normInv = 0
row = 10
precision = 10
iterations = 200

for i in range(iterations):
	C = [[floatRand() for i in range(row)] for i in range(row)]
	b = [floatRand() for i in range(row)]
	C = roundsd(C,precision)
	b = roundsd(b,precision)
	
	trueSolt = LUSolve(C, b, 20)	
	my_normLU = norm( addRows(trueSolt, scalarTimesRow( LUSolve(C,b,precision), -1, 20 ) , 20) ) / norm(trueSolt)
	my_normInv = norm( addRows(trueSolt, scalarTimesRow( InvSolve(C,b,precision), -1, 20 ) , 20) ) / norm(trueSolt)

	normLU = normLU + my_normLU
	normInv = normInv + my_normInv


avgLU = normLU / iterations
avgInv = normInv / iterations

print avgLU,'\t',avgInv
