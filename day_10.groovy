import utils.Matrix



//def input = getExample()
//def input = getExample2()
def input = getInput()

def res1 = part1(input)
println "res1: $res1"

def res2 = part2(input)
println "res2: $res2"


// ---
def part1(def input) {
	def count = [0,0,0,1]

	def prev = 0
	input.sort().each {
		//println "test $prev -> $it : ${it - prev}"
		count[it - prev]++
		prev = it
	}

	//println count
	return count[1] * count[3]
}

def part2(def input) {
	def res = reverseSolver(input)
	return res[0]
}


// [converter:subpaths]
def getSubpaths(def input) {
	def paths = [:]

	input = [0] + input // start at converter 0

	input.each { val ->
		def p = []
		for ( i in 1..3 ) {
			if ( input.contains(val + i) ) p << (val + i)
		}
		paths[val] = p
	}

	paths[ input.max() ] = input.max() + 3  // end at converter 3 more then the max input

	return paths
}

// converter -> count of paths to the end
def reverseSolver( def input ) {
	def subpaths = getSubpaths(input) 
	def counts = [:] // converter -> number of paths to the end

	// given that there is only one path from the max input
	counts[ input.max() + 3 ] = 1 

	subpaths.keySet().sort().reverse().each { c ->
		def paths = subpaths[c]
		Long count = 0
		paths.each { count += counts[it] }
		counts[c] = count	
	}

	println "subpaths:"
	println subpaths

	println "counts:"
	println counts

	return counts

}

//----
def part2Matrix(def input) {
	input = input.sort()

	def m = getSubpathMatrix(input)
	Matrix.printMatrix(m)
	println "---"
	Matrix.printMatrixWolfram(m)

	return false
}


 
/*
represent the count of paths from a converter to the end as a linear system.

let 'px' be the number of paths from converter 'x' to the end
we then have a linear system.  For example, with [4,5,6,7,10,11]:
p4 = p5 + p6 + p7
p5 =      p6 + p7
p6 =           p7
P7 =              p10
p10 =                  p11 + p12
*/
def getSubpathMatrix(def input) {
	def branch = input
	def m = new int[branch.size()][branch.size()+1]
	
	// last converter has one path
	m[branch.size()-1][branch.size()] = 1

	branch.eachWithIndex { val, r ->
		m[r][r] = 1

		for ( i in 1..3 ) {
			def c = branch.indexOf(val + i) // -1 if not found
			if ( c != -1 ) {
				m[r][c] = -1
			}
		}
	}
	return m
}


// ---inputs

def getExample3() {
	return [
	1,
	3, 
	4,
	7,
	]
}

def getExample() {
	return [
16,
10,
15,
5,
1,
11,
7,
19,
6,
12,
4,
	]
}

def getExample2() {
	return [
28,
33,
18,
42,
31,
14,
46,
20,
48,
47,
24,
23,
49,
45,
19,
38,
39,
11,
1,
32,
25,
35,
8,
17,
7,
9,
4,
2,
34,
10,
3,
	]
}

def getInput() {
return [
114,
51,
122,
26,
121,
90,
20,
113,
8,
138,
57,
44,
135,
76,
134,
15,
21,
119,
52,
118,
107,
99,
73,
72,
106,
41,
129,
83,
19,
66,
132,
56,
32,
79,
27,
115,
112,
58,
102,
64,
50,
2,
39,
3,
77,
85,
103,
140,
28,
133,
78,
34,
13,
61,
25,
35,
89,
40,
7,
24,
33,
96,
108,
71,
11,
128,
92,
111,
55,
80,
91,
31,
70,
101,
14,
18,
12,
4,
84,
125,
120,
100,
65,
86,
93,
67,
139,
1,
47,
38,
]
}

