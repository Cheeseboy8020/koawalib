package com.asiankoala.koawalib.path

object Testing {
    @JvmStatic
    fun main(args: Array<String>) {
//        val t = QuinticHermitePath(
//            Pose(-60.0, -10.0, 0.0),
//            Pose(-10.0, -35.0, 270.0.radians),
//            Pose(-30.0, -60.0, 180.0.radians),
//            Pose(-59.0, -10.0, 90.0.radians)
//        )
//
//        var lastProj = 0.0
//        for(i in 0..t.length.toInt()) {
//            val s = i.toDouble()
////            val real = t[s].vec
////            val projV = t.project(t[s].vec, lastS)
////            lastS = projV
////            val proj = t[projV].vec
//
//            val real = t[s].vec
//
//            val deriv = t[s, 1].vec
//            val normal = deriv.rotate(PI / 2.0)
//            val fakeRobot = normal.unit * 1.0 + real
//            val proj = t.project(fakeRobot, lastProj)
//            lastProj = proj
//            val projPoint = t[proj].vec
//            val rVec = projPoint - fakeRobot
//            val error = rVec.norm * (rVec cross deriv).sign
//            val kN = 0.7
//            val gvf = (deriv - normal * error * kN).unit
//            println("x: $fakeRobot, p: $projPoint, r: $rVec, e: ${String.format("%.2f", error)}, gvf: $gvf")
////            println("real: $rVec, proj: $proj, projV: $projV, deriv: $deriv, normal: $normal, delta: ${rVec.dist(proj)}")
//        }
//        println(t.length)

        // f    = 2x^2 + x + 5   @ 2 = 15
        // f'   = 4x + 1         @ 2 = 9
        // f''  = 4              @ 2 = 4
        val polynomial = object : Polynomial() {
            override val coeffVec: List<Double>
                get() = listOf(5.0, 2.0, -3.0, 10.0, 4.0, -6.0)
        }

        val y = polynomial[2.0, 0]
        println(" = $y")
        println(polynomial)
    }
}