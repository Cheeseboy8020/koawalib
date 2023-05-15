package com.asiankoala.koawalib.path.fieldDStar

import com.asiankoala.koawalib.path.fieldDStar.FieldDStar.Companion.constructInterpolationTable

fun main(){
    val array: Array<Double> = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    val func = constructInterpolationTable(1, 0.0, array)
    print(func.value(0.0, 0.0, 28.0))
}