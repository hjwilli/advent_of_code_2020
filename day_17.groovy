

//def input = getExample()
def input = getInput()

def res1 = part1(input)
println "res1: $res1"

def res2 = part2(input)
println "res2: $res2"


// ---
def part1(def input) {
	g = new Grid(input)
	g.prettyPrint()

	for (x in (1..6)) {
		println "round: $x, active points: ${g.active.size()}"
		updateGrid(g)
	}

	return g.active.size()
}

def part2(def input) {
	g = new Grid4D(input)
	g.prettyPrint()

	for (x in (1..6)) {
		println "round: $x, active points: ${g.active.size()}"
		updateGrid(g)
	}

	return g.active.size()
}

def updateGrid(def g) {
	// If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
	// If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive

	Set newActive = [] // set to active
	Set newInactive = [] // set to inactive
	def count

	//println "--start update"
	def coords = g.activeCoords()
	//println "--get got active coords"
	coords.each {c ->
		count = g.activeNeighbors(c)
		if ( g.isActive(c) ) {
			if ( count != 2 && count != 3 ) newInactive << c
		}
		else {
			if ( count == 3 ) newActive << c
		}

	}
	//println "--add: ${newActive.size()} remove: ${newInactive.size()}"

	g.active.addAll( newActive )
	g.active.removeAll( newInactive )
}


class Grid {

	Set active = [] // x,y,z coords of all active nodes 

	def Grid(def input) {
		// string that represents a plain of active nodes
		def y = 0
		def z = 0
		input.eachLine { line ->
			line.trim().toCharArray().eachWithIndex {c, x ->
				if (c == "#") active << [x, y, z]
			}
			y++
		}
	}

	/*
	Return then neighbors for point c [x, y, z]
	*/
	static def getNeighbors(def c) {
		def n = []

		for (i in (-1..1)) {
			for (j in (-1..1)) {
				for (k in -1..1) {
					if ( !((i == 0) && (j == 0) && (k==0)) ) {
						n << [c[0] + i, c[1] + j, c[2] + k]
					}
				}
			}
		}

		return n
	}

	def isActive( def c ) {
		return ( c in this.active )
	}

	// return count of active neighbors
	def activeNeighbors( def c ) {
		Set n = this.getNeighbors(c)
		return n.count { this.active.contains(it) }
	}


	// [x:[min, max], y:[min, max], z:[min:max]]
	def activeRange() {
		def x = this.active.collect{ it[0] }
		def y = this.active.collect{ it[1] }
		def z = this.active.collect{ it[2] }

		return [
			x:[x.min() - 1, x.max() + 1],
			y:[y.min() - 1, y.max() + 1],
			z:[z.min() - 1, z.max() + 1],
		]

	}

	// list of points in the active range
	def activeCoordsGreedy() {
		def r = this.activeRange()
		def points = []

		for (x in (r.x[0]..r.x[1])) {
			for (y in (r.y[0]..r.y[1])) {
				for (z in (r.z[0]..r.z[1])) {
					points << [x, y, z]
				}
			}
		}
		return points
	}

	def activeCoords() {
		//active points and neighbors of all active points
		def points = this.active.collect() as Set

		this.active.each { c ->
			points.addAll( this.getNeighbors(c) )
		}
		return points
	}

	def prettyPrint() {
		println active
	}
}


class Grid4D {

	def active = [] // x,y,z coords of all active nodes 

	def Grid4D(def input) {
		// string that represents a plain of active nodes
		def y = 0
		def z = 0
		def w = 0
		input.eachLine { line ->
			line.trim().toCharArray().eachWithIndex {c, x ->
				if (c == "#") active << [x, y, z, w]
			}
			y++
		}
	}

	/*
	Return then neighbors for point c [x, y, z, w]
	*/
	static def getNeighbors(def c) {
		def n = []

		for (i in (-1..1)) {
			for (j in (-1..1)) {
				for (k in -1..1) {
					for (l in -1..1) {
						if ( !((i == 0) && (j == 0) && (k==0)  && (l==0)) ) {
							n << [c[0] + i, c[1] + j, c[2] + k, c[3] + l]
						}
					}
				}
			}
		}

		return n
	}

	def isActive( def c ) {
		return (c in this.active )
	}

	// return count of active neighbors
	def activeNeighbors( def c ) {
		Set n = this.getNeighbors(c)
		return n.count { this.active.contains(it) }
	}


	// [x:[min, max], y:[min, max], z:[min:max], w:[min:max]]
	def activeRange() {
		def x = this.active.collect{ it[0] }
		def y = this.active.collect{ it[1] }
		def z = this.active.collect{ it[2] }
		def w = this.active.collect{ it[3] }

		return [
			x:[x.min() - 1, x.max() + 1],
			y:[y.min() - 1, y.max() + 1],
			z:[z.min() - 1, z.max() + 1],
			w:[w.min() - 1, w.max() + 1],
		]

	}

	// list of points in the active range
	def activeCoords() {
		//active points and neighbors of all active points
		def points = this.active.collect() as Set

		this.active.each { c ->
			points.addAll( this.getNeighbors(c) )
		}
		return points
	}

	def prettyPrint() {
		println active
	}
}


// ---inputs
def getExample() {
	return '''\
.#.
..#
###
'''
}

def getInput() {
	return '''\
...#..#.
#..#...#
.....###
##....##
......##
........
.#......
##...#..
'''

}

