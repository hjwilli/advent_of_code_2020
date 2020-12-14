
x = "x"

//Example [5, 295], 1068781
//def t = 939
//def bus_ids = [7,13,59,31,19]
//def schedule = [7,13,x,x,59,x,31,19]

//Problem
def t = 1001612
def bus_ids = [19,41,37,821,13,17,29,463,23]
def schedule = [19,x,x,x,x,x,x,x,x,41,x,x,x,37,x,x,x,x,x,821,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,17,x,x,x,x,x,x,x,x,x,x,x,29,x,463,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,23]



def res = problem_13_part1(t, bus_ids)
println ("===part 1===")
println ("best_wait: $res.wait")
println ("best_id: $res.id")
println ("answer: ${res.wait * res.id}")


println ("===part 2===")
def systems = problem_13_part2(schedule)

// solve the system; assumes that all modulus are coprime
def s_combo = systems.first()
systems.eachWithIndex { s_next, idx ->
    if (idx == 0) return
    s_combo = two_system_solver(s_next, s_combo)
}

//result
t = s_combo.rem
println ("\n\nfinal result: $s_combo, t=$t")

// check that it's right
systems.each { s ->
    println ("s: $s ${t%s.mod == s.rem}")
    println ("\tt % $s.mod = ${t%s.mod}")
}


def problem_13_part1(def t, def bus_ids) {
	def best_wait = null
	def best_bus_id

	for (id in bus_ids) {
		def wait = ((Math.floor(t / id) + 1)*id) - t

		if (wait < best_wait || best_wait == null) {
			best_wait = wait
			best_bus_id = id
		}
	}

	return [wait:best_wait, id:best_bus_id]
}

/*
Return the input of problem 2 as a system of mod constraints
*/
def problem_13_part2(def sch) {
	def res = []
	sch.eachWithIndex {id, idx ->
		if (id != x) {
		    // hack to force positive modulo
		    def rem = (id - idx) as long
		    res << [rem: (rem + (id*idx)) % id, mod:id as long]
		}
	}

	res.each { x -> println("t = ${x.rem} mod ${x.mod}")}
	
	return res
}



def two_system_solver(def s1, def s2) {
	/*
	x = s1.rem      MOD (s1.mod)
	x = s2.rem      MOD (s2.mod)


	for a, b < (s1.mon + s2.mod)
	AND a and b are int:

	x = a * (s1.mod) + s1.rem
	x = b * (s2.mod) + s2.rem

	a * (s1.mod) + s1.rem = b * (s2.mod) + s2.rem

	s1.rem = b * (s2.mod) + s2.rem      MOD(s1.mod)
	*/

    // ONLY WORKS FOR POSITIVE MODULO
	println ("\nsolving \n\t$s1 \n\t$s2")

	def b = 0
	while ( s1.rem != (b * (s2.mod) + s2.rem ) % s1.mod ) { 
		b = b + 1 
		if (b > s1.mod * s2.mod) {
			println("something went wrong! b=$b")
			break
		}
	}

	def t = b * (s2.mod) + s2.rem
    
    println ("b=$b, t=$t")
    println ("t % $s1.mod = ${t%s1.mod}")
    println ("t % $s2.mod = ${t%s2.mod}")

	return [rem: t, mod: s1.mod * s2.mod]
}