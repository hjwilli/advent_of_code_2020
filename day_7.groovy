

def input = getExample()
//def input = getInput()

def res1 = part1(input)
println "res1: $res1"

def res2 = part2(input)
println "res2: $res2"



def part1(def input) {
	return parseRules(input)
}

def part2(def input) {

}

def parseRules(def input) {
	def rules = [:]

	input = input.replaceAll("bags", "bag")
	input = input.replaceAll("\\.", "")

	input.eachLine { line ->
		def r = line.split("contain")

		if (line.contains("no other bag")) {
			rules[r[0].trim()] = []
		}
		else {
			rules[r[0].trim()] = r[1].split(",")*.trim()
		}
	}
	println "$rules"
	//return rules
}

def getExample() {

	return '''\
light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.
'''

}

def getInput() {

}
