#Carlo Hernandez	Math 4242	Prof: Lesnick 
import math
import types

#matrices are represented as lists of lists
#where inner lists represent rows of a matrix

def roundsd(number, prec):
	if prec < 0:
		raise RuntimeError, 'Precision must be non-negative'
	
	#for matrices
	#recursively applies roundsd until it reaches each element in rows
	if isinstance(number,types.ListType):
		return [roundsd(x,prec) for x in number]
	
	#for floats
	#moves decimal point all the way to the left and calls round()
	#with precision of prec, then moves the decimal back.
	if isinstance(number,types.FloatType) \
		or isinstance(number,types.IntType):
			count = 0
			while math.fabs(number) > 1:
				number = number/10.0
				count = count + 1
			return round(number, prec) * math.pow(10,count)
			
def scalarTimesRow(row,sc,t):
	scalar = roundsd(sc, t)
	#multiplies each element x in row by sc and returns resulting row
	new_row = map((lambda x: roundsd(x*scalar, t)),row)
	return new_row

def addRows(row1,row2,t):
	if len(row1) != len(row2):
		raise RuntimeError, 'row1 + row2 Undefined'
	r1, r2 = roundsd(row1,t), roundsd(row2,t)
	#adds x in row1 to y in row 2 and returns the row result
	new_row = map((lambda x,y: roundsd(x+y, t)),r1,r2)
	return new_row

def rowColProduct(row,col,t):
	if len(row) != len(col):
		raise RuntimeError, 'row * col Undefined'
	r, c = roundsd(row,t), roundsd(col,t)	
	#create a new_row where each element in the row is multiplied by
	#the corresponding element in the column
	new_row = map((lambda x,y: roundsd(x*y, t)),r,c)
	#sum all the elements in the new_row
	sc = reduce((lambda x,y: roundsd(x+y, t)),new_row)
	return sc
	
def matrixProduct(A,B,t):
	if len(A[0]) != len(B):
		raise RuntimeError, 'AB Undefined'
	a, b = roundsd(A, t), roundsd(B, t)
	colB = transpose(b)
	#entrywise view of matrix multiplication
	AB = [[rowColProduct(row,col,t) for col in colB] for row in a]
	return AB

def rowEform(A,t):
	new_matrix = roundsd(A, t)
	row_length = len(A[0])
	
	P = [i+1 for i in range(len(A))]	# create permutation vector
	E = I(len(A))						# create elementary matrix
	
	pivot = 0;
	for i in range(len(new_matrix) - 1):
		#partial pivoting and checks for column of 0
		while pivot < row_length:
			b = largestPivotIndex(new_matrix[i:],pivot);
			new_matrix[i:] = swap(new_matrix[i:],0,b)
			P[i:] = swap(P[i:],0,b)
			E[i:] = swap(E[i:],0,b)
			if new_matrix[i:][0][pivot] != 0:
				break
			pivot = pivot + 1
		
		if pivot == row_length:
			break
			
		row = new_matrix[i]
		#Gaussian Elimination: lower row calculation
		for j in range(len(new_matrix[i:]) - 1):	#exclude last row
			h = j + i + 1							#index for lowerrows
			lower = new_matrix[h]
			#elementary row operation that clears entries below pivot 	
			sc = roundsd(lower[pivot]/row[pivot], t)	#scalar
			scalarRow = scalarTimesRow(row[pivot:], (-1*sc), t)
			lower[pivot:] = addRows(lower[pivot:],scalarRow, t)
			
			lower[pivot] = sc	#store scalar into pivot position
			
			#elemantary matrix calculation
			scalarErow = scalarTimesRow(E[i], (-1*sc), t)
			E[h] = addRows(E[h],scalarErow, t)
		pivot = pivot + 1
	return [new_matrix, P, E]	

def GEPP(A,t):
	new_matrix = roundsd(A, t)
	# Get LU matrix and P vector 
	[new_matrix, P , E] = rowEform(A, t)
	
	#A not neccesarily square, but L is. Remove 0 columns in L resulting 
	#from GEPP
	L = transpose( lowerTriangle(new_matrix) )
	L = filter(lambda x: not isRowZero(x), L)
	L = transpose(L)
	
	return [L , upperTriangle(new_matrix), P]

def FSolve(L,b,t):
	# error checking
	for i, row in enumerate(L):
		# checks if diagonal is 0
		if row[i] == 0:
			raise RuntimeError,'L is not lower triangular'
		# checks if elements above diagonal is not 0
		for elem in row[i+1:]:
			if elem != 0:
				raise RuntimeError,'L is not lower triangular'
	
	l = roundsd(L, t)
	# create row vector of 0
	x = [0 for i in range(len(l))]
	for i, row in enumerate(l):
		#store element in b corresponding with row in my_sum
		#minus my_sum by the sum of the products of the left elements 
		#by their corresponding elements in x; store result in x[i] 
		my_sum = b[i]
		for j, elem in enumerate(row[:i]): 
			my_sum = roundsd(my_sum - elem * x[j], t)
		x[i] = my_sum
	return x

def BSolve(U,b,t):
	#error checking
	for i, row in enumerate(U):
		#check if diagonal is 0
		if(row[i] == 0):
			raise RuntimeError,'U is not upper triangular'
		#check if elements below diagonal are not 0
		for elem in row[:i]:
			if elem != 0:
				raise RuntimeError,'U is not upper triangular'
	
	u = roundsd(U, t)
	#create row vector of 0
	x = [0 for i in range(len(u))]
	for j in range(len(u)): 
		i = len(u) - j - 1	#index goes backwards
		row = u[i]
		my_sum = b[i]
		#store element in b corresponding with row in my_sum
		#minus my_sum by the sum of the products of the right elements 
		#by their corresponding elements in x; store result in my_sum
		for h in range(len(row)-i):			#right elems get used
			k = len(row) - h - 1			#index goes backwards
			my_sum = roundsd(my_sum - row[k] * x[k], t)
		x[i] = roundsd(my_sum/row[i], t)	#diagonals not always 1
	return x

def LUSolve(A,b,t):
	my_A = roundsd(A,t)
	my_b = roundsd(b,t)
	[L,U,P] = GEPP(my_A,t)
	
	#turns b vector into a column vector, multiplies it by permuted
	#identity to t precision, and turns it back into a row vector
	[my_b]=transpose(matrixProduct(permutedI(P), transpose([my_b]), t))
	out = BSolve(U, FSolve(L, my_b, t), t)
	return roundsd(out, t)

def Invert(A,t):
	if len(A) != len(A[0]):
		raise RunTimeError, 'A is not square'
	
	my_A = roundsd(A,t)
	# Get RowE form with elementary matrix E
	[new_m, P, E] = rowEform(my_A,t)
	new_m = upperTriangle(new_m)
	inv = E
	
	
	for row in new_m:
		if isRowZero(row):
			raise RuntimeError, 'A is singular'
	
	height = len(new_m)
	pivot = height - 1
	
	for j in range(height):
		i = height - j - 1 	#index goes backwards
		
		# check if pivot is actually a pivot
		while not isRowZero(new_m[i][:pivot]):
			pivot = pivot - 1
		
		sc = roundsd(1/new_m[i][pivot], t)	
		new_m[i] = scalarTimesRow(new_m[i], sc, t)
		inv[i] = scalarTimesRow(inv[i], sc, t)
		
		for h in range(i):
			sc = new_m[h][pivot]
			my_row = scalarTimesRow(new_m[i] , (-1*sc), t)
			new_m[h] = addRows(new_m[h], my_row, t)
			
			my_row = scalarTimesRow(inv[i], (-1*sc), t)
			inv[h] = addRows(inv[h], my_row, t)
	
	return inv
			
def InvSolve(A,b,t):
	my_A = roundsd(A,t)
	my_b = roundsd(transpose([b]), t) # turn b into a column vector
	[out] = transpose(matrixProduct(Invert(A, t), my_b, t)) # (A^-1)*b 
	return out
	
#extra functions to help things along
def swap(A,a,b):
	#swaps ath and bth rows
	A[a] , A[b] = A[b], A[a]
	return A
	
def transpose(A):
	#switch ijth element with jith element
	return [[row[count] for row in A] for count in range(len(A[0]))]

def largestPivotIndex(A,pivot):
	largest = 0
	largestIndex = 0
	for index, row in enumerate(A):
		if math.fabs(row[pivot]) > largest:
			largest = math.fabs(row[pivot])
			largestIndex = index
	return largestIndex

def lowerTriangle(A):
	# lower triangle form with 1s diagonal
	new_matrix = []
	for i, row in enumerate(A):
		new_row = []
		for j, elem in enumerate(row):
			if  i == j:
				new_row = new_row + [1]
			elif i < j:
				new_row = new_row + [0]
			else:
				new_row = new_row + [elem]
		new_matrix = new_matrix + [new_row]
	return new_matrix

def upperTriangle(A):
	# upper triangle form
	new_matrix = []
	for i, row in enumerate(A):
		new_row = []
		for j, elem in enumerate(row):
			if i > j:
				new_row = new_row + [0]
			else:
				new_row = new_row + [elem]
		new_matrix = new_matrix + [new_row]
	return new_matrix

def I(n):
	new_matrix = []
	for i in range(n):
		new_row = []
		for j in range(n):
			if i == j:
				new_row = new_row + [1]
			else:
				new_row = new_row + [0]
		new_matrix = new_matrix + [new_row]
	return new_matrix
	
def permutedI(P):
	# create permuted identity using permutation vector P
	n = len(P)
	new_matrix = [[0 for i in range(n)]for j in range(n)]
	for h, x in enumerate(P):
		new_matrix[h][x-1] = 1
	return new_matrix

def isRowZero(row):
	if row == []:
		return True
	return row[0] == 0 and isRowZero(row[1:])

def toString(A):
	if isinstance(A,types.ListType):
		return [toString(x) for x in A]
	else:
		return str(A)
		
def mPrint(A):
	strList = toString(A)
	for row in strList:
		for elem in row:
			print elem,'\t',
		print''	

def matrixInput():
	row, col = input ('What is the dimensions of your matrix (seperate by comma) ') 
	A = []
	for i in range(row):
		new_row = []
		for j in range(col):
			elem = ''
			isNumber = False
			while not isNumber:
				try:
					print 'Enter number for element in matrix position',i+1,' ',j+1,':'
					elem = float(raw_input())
					isNumber = True
				except ValueError:
					print 'Not a number. Try again'
					isNumber = False			
			new_row = new_row + [elem]
		A = A + [new_row]
	return A
	
def vectorInput(size):
	b = []
	for i in range(size):
		elem = ''
		isNumber = False
		while not isNumber:
			try:
				print 'Enter number for element in vector positon',i+1,':'
				elem = float(raw_input())
				isNumber = True
			except ValueError:
				print 'Not a number. Try again'
				isNumber = False
		b = b + [elem]
	return b
