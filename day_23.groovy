

//def input = getExample2()
def input = getInput()

def res1 = part1(input)
println "res1: $res1"
assert res1 == "432796815"

def res2 = part2(input)
println "res2: $res2"


// ---
//54327968
def part1(def input) {
	def cups = playGame(input)
	return playGame(input).join("")
}

def part2(def input) {
	def bigInput = (0..1000000).collect()
	//def bigInput = (0..100).collect()
	input.eachWithIndex{ v, i -> bigInput[i] = v }

	//println bigInput
	//return

	println "starting game"
	def cups = playGame(bigInput, 10000)
	//def cups = playGame(bigInput, 10000000)

	def idxOne = cups.indexOf(1)
	def idxA = (idxOne + 1) % cups.size()
	def idxB = (idxOne + 2) % cups.size()

	println "idxOne: $idxOne, idxA: $idxA, idxB: $idxB"
	println "vals: ${cups[idxA]}, ${cups[idxB]}"
	println "val[idx1]: ${cups[idxOne]}"

	Long res = (cups[idxA] as Long) * (cups[idxB] as Long)
	return res

}


def playGame(def cups, def maxMoves = 100) {

	def s = cups.size()

	def curVal = cups[0]
	def cur
	def rem
	def remIdx
	def destVal

	def start = System.currentTimeMillis()

	for(move in 1..maxMoves ) {
		if (move % 1000 == 0) {
			println "-- ${ (System.currentTimeMillis() - start) / 1000F} move $move ${(100*move)/maxMoves}% --"
		}

		println "-- move $move --"
		//println "cups: $cups"
		////println "calc"
		////println "${ (System.currentTimeMillis() - start) / 1000F}"

		cur = cups.indexOf(curVal)
		rem = [cups[(cur+1)%s], cups[(cur+2)%s], cups[(cur+3)%s]]
		//remIdx = [(cur+1)%s, (cur+2)%s, (cur+3)%s]
		destVal = (cups[cur] - 1) % s
		
		//println "curVal $curVal, rem:$rem, destVal:$destVal, curIdx: $cur"
		//println "curVal $curVal"
		//println "pick up: $rem"

		// remove cups
		////println "rem start"
		////println "${ (System.currentTimeMillis() - start) / 1000F}"
		//remIdx.sort().reverse().each { println "removing $it"; cups.removeAt(it) }
		//rem.each { cups.removeElement(it) }
		rem.each { cups = cups.minus(it) }
		//cups = cups.minus(rem) //works
		//cups.removeAll(rem)
		////println "removed"
		////println "${ (System.currentTimeMillis() - start) / 1000F}"

		// get valid dest
		while (!cups.contains(destVal)) destVal = (destVal - 1) > 0 ? destVal - 1 : s 
		////println "cups: $cups newDestVal: $destVal"
		////println "destination: $destVal"
		////println "${ (System.currentTimeMillis() - start) / 1000F}"
		
		// add cups
		cups.addAll((cups.indexOf(destVal) + 1)%s, rem)
		curVal = cups[(cups.indexOf(curVal) + 1)%s]
	}

	return cups
}



// ---inputs
def getExample2() {
	return [
		5,
		4,
		3,
		2,
		1,
	]
}

def getExample() {
//return "389125467"
return [
	3,
	8,
	9,
	1,
	2,
	5,
	4,
	6,
	7,
	]
}

def getInput() {
//return "538914762"
return [
	5,
	3,
	8,
	9,
	1,
	4,
	7,
	6,
	2,
	]
}
