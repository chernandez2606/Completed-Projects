#Carlo Hernandez	Math 4242	Prof: Lesnick 
execfile("matrix_algebra.py")

row = [4.325,.0001,.0237]
sc = 100.4
t = 3
print toString(scalarTimesRow(row,sc,t)),'\n' #[433, .01, 2.37]

row = [4.325,.0001,.0237]
sc = 101.4
t = 3
print toString(scalarTimesRow(row,sc,t)),'\n' #[437, .0101, 2.39]

A = [0.9340, 0.6787, 0.7577]
B = [0.7431, 0.3922, 0.6555]
t = 3
print toString(addRows(A,B,t)),'\n' #[1.68 1.07 1.41]

A = [	[1,2,1,3,3],	\
		[2,4,0,4,4],	\
		[1,2,3,5,5],	\
		[2,4,0,4,7]		]
t = 3
[L,U,P] = GEPP(A, t)
mPrint(L)
print''
mPrint(U)
print '\n',toString(P),'\n'

#1  	0   	0   	0 	
#0.5 	1   	0   	0 	
#0.5 	0.0 	1   	0 	
#1.0 	0.0 	0.0 	1 	

#2.0 	4.0 	0.0 	4.0 	4.0 	
#0  	0.0 	3.0 	3.0 	3.0 	
#0  	0   	0.333 	0.001 	0.001 	
#0  	0   	0   	0.0 	3.0 	

#['2', '3', '1', '4'] 
