package com.asiankoala.koawalib.path.fieldDStar

import org.apache.commons.math3.analysis.interpolation.TricubicInterpolatingFunction
import org.apache.commons.math3.analysis.interpolation.TricubicInterpolator
import java.lang.Math.pow
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
* @link https://www.ri.cmu.edu/pub_files/pub4/ferguson_david_2005_3/ferguson_david_2005_3.pdf
*/
class FieldDStar {
    companion object {
        @JvmStatic
        fun constructInterpolationTable(
            nC: Int,
            mC: Double,
            cellCosts: Array<Double>
        ): TricubicInterpolatingFunction {
            val table = TricubicInterpolator()
            val cIArray = ArrayList<Double>()
            val bIArray = ArrayList<Double>()
            val fArray = ArrayList<Double>()
            val vArray = Array(nC){Array(nC){DoubleArray(mC.toInt())} }
            var cI = 0
            while (cI < nC) {
                val c = cellCosts[cI]
                var bI = 0
                while (bI < nC) {
                    val b = cellCosts[bI]
                    var f = 1
                    while (f <= mC) {
                        if (f < b) {
                            if (c <= f) {
                                cIArray[cI] = cI.toDouble()
                                cIArray.add(cI.toDouble())
                                bIArray.add(bI.toDouble())
                                fArray.add(f.toDouble())
                                vArray[cI][bI][f-1] = c * sqrt(2.0)
                            } else {
                                val y = min(f / (sqrt(c.pow(2.0) - f.toDouble().pow(2.0))), 1.0)
                                cIArray.add(cI.toDouble())
                                bIArray.add(bI.toDouble())
                                fArray.add(f.toDouble())
                                vArray[cI][bI][f-1] = (c * sqrt(1 + y.pow(2.0)) + f * (1 - y))
                            }
                        } else {
                            if (c <= b) {
                                cIArray.add(cI.toDouble())
                                bIArray.add(bI.toDouble())
                                fArray.add(f.toDouble())
                                vArray[cI][bI][f-1] = (c * sqrt(2.0))
                            } else {
                                val x = 1 - min(b / (sqrt(c.pow(2.0) - b.pow(2.0))), 1.0)
                                cIArray.add(cI.toDouble())
                                bIArray.add(bI.toDouble())
                                fArray.add(f.toDouble())
                                vArray[cI][bI][f-1] = (c * sqrt(1 + (1 - x).pow(2.0)) + b * x)
                            }
                        }
                        f++
                    }
                    bI++
                }
                cI++
            }
            return table.interpolate(cIArray.distinct().toDoubleArray(),bIArray.distinct().toDoubleArray(),fArray.distinct().toDoubleArray(),vArray)
        }
    }

    external fun getMat(): Array<DoubleArray>
}