//
// Sponsored License - for use in support of a program or activity
// sponsored by MathWorks.  Not for government, commercial or other
// non-sponsored organizational use.
// File: main.cpp
//
// MATLAB Coder version            : 5.6
// C/C++ source code generated on  : 15-May-2023 01:38:09
//

/*************************************************************************/
/* This automatically generated example C++ main file shows how to call  */
/* entry-point functions that MATLAB Coder generated. You must customize */
/* this file for your application. Do not modify this file directly.     */
/* Instead, make a copy of this file, modify it, and integrate it into   */
/* your development environment.                                         */
/*                                                                       */
/* This file initializes entry-point function arguments to a default     */
/* size and value before calling the entry-point functions. It does      */
/* not store or use any values returned from the entry-point functions.  */
/* If necessary, it does pre-allocate memory for returned values.        */
/* You can use this file as a starting point for a main function that    */
/* you can deploy in your application.                                   */
/*                                                                       */
/* After you copy the file, and before you deploy it, you must make the  */
/* following changes:                                                    */
/* * For variable-size function arguments, change the example sizes to   */
/* the sizes that your application requires.                             */
/* * Change the example values of function arguments to the values that  */
/* your application requires.                                            */
/* * If the entry-point functions return values, store these values or   */
/* otherwise use them as required by your application.                   */
/*                                                                       */
/*************************************************************************/

// Include Files
#include "main.h"
#include "getMat.h"
#include "getMat_terminate.h"

// Function Definitions
//
// Arguments    : int argc
//                char **argv
// Return Type  : int
//
int main(int, char **)
{
    // The initialize function is being called automatically from your entry-point
    // function. So, a call to initialize is not included here. Invoke the
    // entry-point functions.
    // You can call entry-point functions multiple times.
    main_getMat();
    // Terminate the application.
    // You do not need to do this more than one time.
    getMat_terminate();
    return 0;
}

//
// Arguments    : void
// Return Type  : void
//
void main_getMat()
{
    static double mat[2073600];
    // Call the entry-point 'getMat'.
    getMat(mat);
}

//
// File trailer for main.cpp
//
// [EOF]
//
