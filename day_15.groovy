def input = [ 0,3,6]
//def input = [0,13,16,17,1,10,6]

def res

res = memory_game(input, 2020)
println "part 1 result: $res"

res = memory_game(input, 30000000)
println "part 2 result: $res"


def memory_game(def input, def max_turn) {
	def mem = [:]
	def prev = input.removeLast()
	def say

	input.eachWithIndex { i, idx ->
		mem[i] = idx + 1 
	}

	def turn = input.size() + 1

	//println ("t: $turn - $prev - $mem")

	while ( turn < max_turn ) {
		if (mem.keySet().contains(prev)) {
			//println "t: ${turn+1} contains! $prev  $turn - ${mem.get(prev)}"
			say = turn - mem[prev]
		}
		else { say = 0 }

		mem[prev] = turn 
		prev = say
		turn = turn + 1

		//println ("t: $turn - $prev - $mem")
	}

	return prev
}
