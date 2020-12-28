

//def input = getExample()
def input = getInput()


def res1 = part1(input.collect())
println "res1: $res1"

assert res1 == "54327968" // input
//assert res1 == "67384529"// example

def res2 = part2(input.collect())
println "res2: $res2"


// ---
def part1(def input) {
	//return playGame(input).join("")

	def cups = playGameLinkedList(input)
	
	def res = ""
	def cur = cups[1].n

	while (cur != 1) {
		res += cur
		cur = cups[cur].n
	}

	return res


}

def part2(def input) {
	def bigInput = (1..1000000).collect()
	input.eachWithIndex{ v, i -> bigInput[i] = v }

	println "starting game"
	def cups = playGameLinkedList(bigInput, 10000000)

	println "finished: "
	println cups.take(10)

	def a = cups[1].n
	def b = cups[a].n

	println "a: $a, b: $b"

	Long res = ( a as Long ) * ( b as Long )
	return res
}

def part2Brute(def input) {
	def bigInput = (1..1000000).collect()
	input.eachWithIndex{ v, i -> bigInput[i] = v }

	println bigInput.take(15)

	println "starting game"
	def cups = playGame(bigInput, 10000000)

	println "finished: 0..15"
	println cups.take(15)

	def idxOne = cups.indexOf(1)
	def idxA = (idxOne + 1) % cups.size()
	def idxB = (idxOne + 2) % cups.size()

	println "cups[10000000]: ${cups.indexOf(1000000)}"
	println "cups[10000000]: ${cups.indexOf(500000)}"
	println "==="
	println "idxOne: $idxOne, idxA: $idxA, idxB: $idxB"
	println "vals: ${cups[idxA]}, ${cups[idxB]}"
	println "val[idx1]: ${cups[idxOne]}"

	println "=="
	def testMax = idxOne + 15 >= cups.size() ? cups.size() :idxOne+15
	println "cups.getAt( $idxOne..$testMax ): ${ cups.getAt(idxOne..testMax) }"

	Long res = (cups[idxA] as Long) * (cups[idxB] as Long)
	return res

}

// linked list
def playGameLinkedList(def cupsList, def maxMoves = 100 ) {

	def s = cupsList.size()

	def cur = cupsList[0]
	def rem

	def a
	def b

	// set up cup linked list
	def cups = [:]
	cupsList.eachWithIndex { v, i ->
		def pIdx = (i-1) % cupsList.size()
		def nIdx = (i+1) % cupsList.size()

		cups[v] = [p:cupsList[pIdx], n:cupsList[nIdx]]
	}


	def start = System.currentTimeMillis()

	for(move in 1..maxMoves ) {
		if (move % 100000 == 0) {
			println "-- ${ (System.currentTimeMillis() - start) / 1000F} move $move ${(100*move)/maxMoves}% --"
		}

		//println "-- move $move --"

		rem = [cups[cur].n]
		rem << cups[rem.last()].n
		rem << cups[rem.last()].n
		
		//println "cur $cur"
		//println "pick up: $rem"

		// remove cups
		a = cups[rem[0]].p
		b = cups[rem[2]].n
		cups[a].n = b
		cups[b].p = a


		// get valid dest
		a = (cur - 1) % s
		while( rem.contains(a) || a == 0 ) a = (a - 1) > 0 ? a - 1 : s
		//println "cups: $cups newDestVal: $a"
		//println "destination: $a"
		
		// add cups
		b = cups[a].n

		cups[a].n = rem[0]
		cups[rem[0]].p = a

		cups[b].p = rem[2]
		cups[rem[2]].n = b

		cur = cups[cur].n
	}

	//println "final: $cups"

	return cups
}

// lists
def playGame(def cups, def maxMoves = 100) {

	def s = cups.size()

	def curVal = cups[0]
	def cur
	def rem
	def remIdx
	def destVal

	def start = System.currentTimeMillis()

	for(move in 1..maxMoves ) {
		if (move % 100000 == 0) {
			println "-- ${ (System.currentTimeMillis() - start) / 1000F} move $move ${(100*move)/maxMoves}% --"
		}

		//println "-- move $move --"
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
		rem.each { cups.removeElement(it) } 
		////println "removed"
		////println "${ (System.currentTimeMillis() - start) / 1000F}"

		//!cups.contains(rem)

		// get valid dest
		while (!cups.contains(destVal)) destVal = (destVal - 1) > 0 ? destVal - 1 : s
		//println "cups: $cups newDestVal: $destVal"
		////println "destination: $destVal"
		////println "${ (System.currentTimeMillis() - start) / 1000F}"
		
		// add cups
		cups.addAll((cups.indexOf(destVal) + 1) % ( s - 3 ), rem)
		curVal = cups[ (cups.indexOf(curVal) + 1) % s ]
	}

	//println "final: $cups"
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
