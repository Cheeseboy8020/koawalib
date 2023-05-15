//
// Sponsored License - for use in support of a program or activity
// sponsored by MathWorks.  Not for government, commercial or other
// non-sponsored organizational use.
// File: circshift.cpp
//
// MATLAB Coder version            : 5.6
// C/C++ source code generated on  : 15-May-2023 01:38:09
//

// Include Files
#include "circshift.h"

// Function Definitions
//
// Arguments    : short a[2073600]
//                const double p[2]
// Return Type  : void
//
namespace coder {
void circshift(short a[2073600], const double p[2])
{
  int stride;
  short buffer[720];
  boolean_T shiftright[2];
  stride = 1;
  for (int k{0}; k < 2; k++) {
    double d;
    int npages;
    int ns;
    int pagesize;
    int u0;
    boolean_T b;
    d = p[k];
    if (d < 0.0) {
      u0 = -static_cast<int>(d);
      b = false;
    } else {
      u0 = static_cast<int>(d);
      b = true;
    }
    if (u0 > 1440) {
      u0 -= 1440 * (u0 / 1440);
    }
    if (u0 > 720) {
      u0 = 1440 - u0;
      b = !b;
    }
    shiftright[k] = b;
    ns = u0 - 1;
    pagesize = stride * 1440;
    npages = -1439 * k + 1439;
    if (u0 > 0) {
      for (int i{0}; i <= npages; i++) {
        int pageroot;
        pageroot = i * pagesize;
        for (int j{0}; j < stride; j++) {
          int i1;
          i1 = pageroot + j;
          if (shiftright[k]) {
            int b_i;
            for (int b_k{0}; b_k <= ns; b_k++) {
              buffer[b_k] = a[i1 + ((b_k - u0) + 1440) * stride];
            }
            b_i = u0 + 1;
            for (int b_k{1440}; b_k >= b_i; b_k--) {
              a[i1 + (b_k - 1) * stride] = a[i1 + ((b_k - u0) - 1) * stride];
            }
            for (int b_k{0}; b_k <= ns; b_k++) {
              a[i1 + b_k * stride] = buffer[b_k];
            }
          } else {
            int b_i;
            for (int b_k{0}; b_k <= ns; b_k++) {
              buffer[b_k] = a[i1 + b_k * stride];
            }
            b_i = 1439 - u0;
            for (int b_k{0}; b_k <= b_i; b_k++) {
              a[i1 + b_k * stride] = a[i1 + (b_k + u0) * stride];
            }
            for (int b_k{0}; b_k <= ns; b_k++) {
              a[i1 + ((b_k - u0) + 1440) * stride] = buffer[b_k];
            }
          }
        }
      }
    }
    stride = pagesize;
  }
}

} // namespace coder

//
// File trailer for circshift.cpp
//
// [EOF]
//
