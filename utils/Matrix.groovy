package utils


class Matrix {
	
	// pretty print a 2d array
	static def printMatrix(def m) {

		println "Matrix size: ${m.size()} x ${m.first().size()}"

		m.each { 
			println it.join(", ")
		}
	}

	// Wolfram Alpha syntax
	static def printMatrixWolfram(def m) {
		def s =  "{"
		m.each { 
			s+= "{" + it.join(", ") + "},"
		}
		s=s.substring(0, s.size()-1)
		s+= "}"

		println s
	}
}