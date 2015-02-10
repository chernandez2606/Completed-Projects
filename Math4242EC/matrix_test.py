#Carlo Hernandez	Math 4242	Prof: Lesnick 
execfile("matrix_algebra.py")

def absMinusRows(row1,row2, t):
	new_row = scalarTimesRow(row2, -1, t)
	new_row = addRows(row1, new_row, t)
	new_row = [math.fabs(elem) for elem in new_row]
	return new_row

#stability of LUSolve and InvSolve with respect to precision
#testing with ill conditioned system
C = [	[.835,.667],	\
		[.333,.266]		]
b = [.168, .067]

LUSolved = []
InvSolved = []

prec = 6
precRange = 14 - prec
for i in range(precRange):
	t = i + prec
	LUSolved = LUSolved + [ LUSolve(C,b,t) ]
	InvSolved = InvSolved + [ InvSolve(C,b,t) ]

for i in range(len(LUSolved)):
	print i+prec, '\t', toString(LUSolved[i]), 	\
		'\t\t\t', toString(InvSolved[i])
print ''

true_sol = [1,-1]	
offLU = [absMinusRows(row, true_sol, i+prec) for i, row in enumerate(LUSolved)]
offInv = [absMinusRows(row, true_sol, i+prec) for i, row in enumerate(InvSolved)]

for i in range(len(LUSolved)):
	print i+prec, '\t', toString(offLU[i]), 	\
		'\t\t\t\t', toString(offInv[i])
#get averages of the differences from the true solution	
offLU = transpose(offLU)
avgLU = [reduce(lambda x,y:x+y, col)/len(col) for col in offLU]

offInv = transpose(offInv)
avgInv = [reduce(lambda x,y:x+y, col)/len(col) for col in offInv]
print '\n',avgLU,'\t',avgInv,'\n'

#stability with respect to permuted versions of C,b
b[1] = .066
print toString(LUSolve(C,b,10)),'\t',toString(InvSolve(C,b,10)),'\n'

#stability of LUSolve and InvSolve 
#with respect to permuted versions of C,b
#testing with well conditioned system
C = [	[1,2],	\
		[2,3]	]
b = [4, 7]

for i in range(10):
	d = [elem -.001*i for elem in b]
	print toString(d),'\t',toString(LUSolve(C,d,4)),'\t',toString(InvSolve(C,d,4))
print ''

#stability of matrix-vector multiplication with respect to precision
C = [	[.835,.667],	\
		[.333,.266]		]
x = [1, -1]
colx = transpose([x])
productSolved = []

prec = 1
precRange = 14 - prec
for i in range(precRange):
	t = i + prec
	productSolved = productSolved + transpose( matrixProduct(C,colx,t) )

for i in range(len(productSolved)):
	t = i + prec
	print t,'\t',toString(productSolved[i])
print ''

#stability with respect to permuted versions of x
colx = transpose([x])
for i in range(10):
	d = [elem -.001*i for elem in x]
	cold = transpose([d])
	print d,'\t', toString( transpose( matrixProduct(C,cold,3) ) )
print ''

#stability of matrix inversion calculation
#in terms of precision

prec = 6
precRange = 14 - prec
for i in range(precRange):
	t = i + prec
	print "precision ",t
	inv = Invert(C,t)
	for row in inv:
		print toString(row)
	print''

D = C[:]
for i in range(5):
	D[0][0] = D[0][0] + .001
	print 'first element =',D[0][0]
	inv = Invert(D,6)
	for row in inv:
		print toString(row)
	print''
print ''


