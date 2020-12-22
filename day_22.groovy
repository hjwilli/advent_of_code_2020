import groovy.transform.Field

//def input = getExample()
//def input = getExample2()
def input = getInput()

//def res1 = part1(input)
//println "res1: $res1"


//input = getInput()
def res2 = part2(input)
println "res2: $res2"


// ---
def part1(def input) {
	def res = playGame(input)

	if (res.p1.size() > 0)
		return calcScore(res.p1)
	else
		return calcScore(res.p2)

}

def calcScore(def deck) {
	def score = 0

	println "score: $deck"

	deck.reverse().eachWithIndex { v, i ->
		score = score + v * (i+1)
	}

	return score

}

def playGame(def input) {
	def p1 = input.p1
	def p2 = input.p2

	def r = 0 

	while ((p1.size() != 0 && p2.size() != 0) && r < 5000) {
		//println "--round $r--"
		//println "p1: $p1"
		//println "p2: $p2"
		
		def a = p1.pop()
		def b = p2.pop()

		//println "$a vs $b"

		if (a > b) {
			p1 << a
			p1 << b
		}
		else {
			p2 << b
			p2 << a
		}
		r++
	}
	println "done:"
	println p1
	println p2

	return [p1: p1, p2:p2]
}

def part2(def input) {
	def res = playRecurse(input)

	if (res.p1.size() > 0)
		return calcScore(res.p1)
	else
		return calcScore(res.p2)
}

def playRecurse(def input, def game = 1) {
	//def p1 = input.p1
	//def p2 = input.p2

	//println "game $input"
	Set states = []

	def r = 1 
	while ((input.p1.size() != 0 && input.p2.size() != 0) ) { // && r < 5000) {
		
		//if (r > 1 && r % 1000 == 0 ) {
		if ( false ) {
			println "--game $game, round $r, states: ${states.size()}--"
			println "p1: ${input.p1}"
			println "p2: ${input.p2}"
		}

		// check for previous configuration
		if( input in states ) {
			//println "-state found"
			return [p1:input.p1, p2:[], win:"p1"]
		}

		states << [p1:input.p1.collect(), p2:input.p2.collect()]
		
		def a = input.p1.pop()
		def b = input.p2.pop()

		//println "$a vs $b"

		if ( a <= input.p1.size() && b <= input.p2.size() ) {
			//println "playing recursive game..."
			def res = playRecurse([p1:input.p1.subList(0,a).collect(), p2:input.p2.subList(0,b).collect()], game+1) 
			//println "back to game, winner: ${res.win}"
			if (res.win == "p1") {
				input.p1 << a
				input.p1 << b
			}
			else {
				input.p2 << b
				input.p2 << a
			}
 
		}
		else {
			//println "playing normal round"
			if (a > b) {
				input.p1 << a
				input.p1 << b
			}
			else {
				input.p2 << b
				input.p2 << a
			}
		}
		r++
	}

	def win
	if (input.p1.size() > 0) win = "p1"
	else win = "p2"
	//println "done:"
	//println p1
	//println p2
	//println win

	return [p1: input.p1, p2:input.p2, win:win]
}


// ---inputs
def getExample2() {
	def p1 = [
		43,
		19,
	]

	def p2 = [
		2,
		29,
		14,
	]

	return [p1:p1, p2:p2]
}

def getExample() {
	def p1 = [
		9,
		2,
		6,
		3,
		1,
	]
	def p2 = [
		5,
		8,
		4,
		7,
		10,
	]

	return [p1:p1, p2:p2]
}

def getInput() {

	def p1 = [
		20,
		28,
		50,
		36,
		35,
		15,
		41,
		22,
		39,
		45,
		30,
		19,
		47,
		38,
		25,
		6,
		2,
		27,
		5,
		4,
		37,
		24,
		42,
		29,
		21,
	]


	def p2 = [
		23,
		43,
		34,
		49,
		13,
		48,
		44,
		18,
		14,
		9,
		12,
		31,
		16,
		26,
		33,
		3,
		10,
		1,
		46,
		17,
		32,
		11,
		40,
		7,
		8,
	]

	return [p1:p1, p2:p2]

}

