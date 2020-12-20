import groovy.transform.ToString 
import groovy.transform.AutoClone

N = 0
E = 1
S = 2
W = 3

def input = getExample2()
//def input = getInput()

def res1 = part1(input)
println "res1: $res1"


def res2 = part2(input)
println "res2: $res2"


// ---

def parseTiles(def input) {
	def tiles = []

	def data = []
	def id = 0
	input.eachLine { line ->
		if (line.trim().size() == 0) {
			def t = new Tile(id.toLong(), data)
			tiles << t
			data = []
		}
		else if (line.contains("Tile")) {
			def res = ( line =~ /Tile (\d+):/)
			id = res.findAll().first()[1]
		}
		else {
			data << line.trim()
		}
	}

	return tiles
}

def part1(def input) {
	def tiles = parseTiles(input)

	println "testing:"

	println tiles.first().getSide(N)
	println tiles.first().getSide(E)
	println tiles.first().getSide(S)
	println tiles.first().getSide(W)

	tiles.first().flip = 1

	println "==="
	println tiles.first().getSide(N)
	println tiles.first().getSide(E)
	println tiles.first().getSide(S)
	println tiles.first().getSide(W)

	return false

	int dim = Math.sqrt(tiles.size()) // dim of picutre
	println "tiles: ${tiles.size()} dim:${dim}"

	def res = picSolver(tiles, dim)

	println "got res: $res"

	def prod = 1
	res.each { prod = prod * it.id}
	return prod
}

def picSolver(def remTiles, def dim, def pic = []) {
	println "ps \n\ttiles: ${remTiles*.id} \n\tpic: ${pic*.id} ${pic*.flip} ${pic*.rot}"

	for (def tile in remTiles) {
		for (flip in [false, true]) {
			for (rot in (0..1)) {
				def testTile = tile.clone()
				testTile.flip = flip
				testTile.rot = rot

				if (isValid (testTile, pic, dim)) {
					def testPic = pic + testTile
					
					// success
					if ((testPic.size()) == dim * dim ) return testPic

					def res = picSolver (remTiles - tile, dim, testPic)
					if (res) { println "success!"; return res }
				}
			}
		}
	}
	return false
}

def isValid(tile, pic, dim) {
	int x = (pic.size() ) % dim
	int y = Math.floor((pic.size() ) / dim)
	def idx

	println "\t\t\tisValid [$x, $y] ${tile.id} f:${tile.flip} r:${tile.rot}"

	// check N
	idx = getIdx(x, y-1, dim)
	if (idx >= 0 ) {
		//println "checking N:"
		//println tile.getSide(N) 
		//println pic[idx].getSide(S)
		if (tile.getSide(N) != pic[idx].getSide(S) ) return false
	}

	/*
	// check E
	idx = getIdx(x+1, y, dim)
	if (idx >= 0 ) {
		if (tile.getSide(E) != pic[idx].getSide(W) ) return false
	}

	// check S
	idx = getIdx(x, y+1, dim)
	if (idx >= 0 ) {
		if (tile.getSide(S) != pic[idx].getSide(N) ) return false
	}
   */
	// check w
	idx = getIdx(x-1, y, dim)
	if (idx >= 0 ) {
		if (tile.getSide(W) != pic[idx].getSide(E) ) return false
	}
	
	//println "\t\t\ttrue"
	return true
}

def getIdx(x, y, dim) {
	if ((x < 0) || (y < 0) || (x >= dim) || (y >= dim)) return -1
	return x + (y * dim)
}

def part2(def input) {

}

@AutoClone
@ToString
class Tile {
	def rawData

	Long id
	def rot = 0 // rot to right: 0-3
	def flip = false

	def sides = [] // N E S W

	static int mask = 0b1111111111

	def Tile(id, rawData) {
		this.id = id
		this.rawData = rawData

		// N
		def rawLine = rawData.first()
		def line = rawLine.replaceAll("\\.", "0").replaceAll("#", "1")
		int s = Eval.me("0b$line") & mask
		sides[0] =  s
		//println "==="
		//println Integer.toBinaryString(s & mask)
		//println Integer.toBinaryString(~s & mask)
		
		// E
		rawLine = ""
		rawData.each { rawLine = rawLine + it.toCharArray()[-1] }
		line = rawLine.replaceAll("\\.", "0").replaceAll("#", "1")
		s = Eval.me("0b$line") & mask
		sides[1] = s

		// S
	 	rawLine = rawData.last()
		line = rawLine.replaceAll("\\.", "0").replaceAll("#", "1")
		s = Eval.me("0b$line") & mask
		sides[2] =  s

		// W
		rawLine = ""
		rawData.each { rawLine = rawLine + it.toCharArray()[-1] }
		line = rawLine.replaceAll("\\.", "0").replaceAll("#", "1")
		s = Eval.me("0b$line") & mask
		sides[3] = s
	}

	def getSide(def val) {
		if (!flip) {
			def idx = (val + rot) % 4
			return sides[idx] & mask
		}
		else {
			def idx = ( (-1 * val - rot ) + 8) % 4
			return ~sides[idx] & mask
		}
	}

}


// ---inputs
def getExample2() {
	def input = '''\
Tile 2311:
0000000000
##########
##########
##########
##########
##########
##########
##########
##########
##########

Tile 2312:
0000000000
##########
##########
##########
##########
##########
##########
##########
##########
##########

Tile 2313:
0000000000
##########
##########
##########
##########
##########
##########
##########
##########
##########

Tile 2314:
0000000000
##########
##########
##########
##########
##########
##########
##########
##########
##########

'''
	return input
}


def getExample() {
	def input = '''\
Tile 2311:
..##.#..#.
##..#.....
#...##..#.
####.#...#
##.##.###.
##...#.###
.#.#.#..##
..#....#..
###...#.#.
..###..###

Tile 1951:
#.##...##.
#.####...#
.....#..##
#...######
.##.#....#
.###.#####
###.##.##.
.###....#.
..#.#..#.#
#...##.#..

Tile 1171:
####...##.
#..##.#..#
##.#..#.#.
.###.####.
..###.####
.##....##.
.#...####.
#.##.####.
####..#...
.....##...

Tile 1427:
###.##.#..
.#..#.##..
.#.##.#..#
#.#.#.##.#
....#...##
...##..##.
...#.#####
.#.####.#.
..#..###.#
..##.#..#.

Tile 1489:
##.#.#....
..##...#..
.##..##...
..#...#...
#####...#.
#..#.#.#.#
...#.#.#..
##.#...##.
..##.##.##
###.##.#..

Tile 2473:
#....####.
#..#.##...
#.##..#...
######.#.#
.#...#.#.#
.#########
.###.#..#.
########.#
##...##.#.
..###.#.#.

Tile 2971:
..#.#....#
#...###...
#.#.###...
##.##..#..
.#####..##
.#..####.#
#..#.#..#.
..####.###
..#.#.###.
...#.#.#.#

Tile 2729:
...#.#.#.#
####.#....
..#.#.....
....#..#.#
.##..##.#.
.#.####...
####.#.#..
##.####...
##..#.##..
#.##...##.

Tile 3079:
#.#.#####.
.#..######
..#.......
######....
####.#..#.
.#...#.##.
#.#####.##
..#.###...
..#.......
..#.###...

'''

	return input
}

def getInput() {
}

