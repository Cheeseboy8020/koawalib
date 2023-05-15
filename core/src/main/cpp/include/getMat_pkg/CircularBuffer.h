//
// Sponsored License - for use in support of a program or activity
// sponsored by MathWorks.  Not for government, commercial or other
// non-sponsored organizational use.
// File: CircularBuffer.h
//
// MATLAB Coder version            : 5.6
// C/C++ source code generated on  : 15-May-2023 01:38:09
//

#ifndef CIRCULARBUFFER_H
#define CIRCULARBUFFER_H

// Include Files
#include "rtwtypes.h"
#include <cstddef>
#include <cstdlib>

// Type Declarations
namespace coder {
namespace matlabshared {
namespace autonomous {
namespace internal {
class CircularBufferIndex;

}
} // namespace autonomous
} // namespace matlabshared
} // namespace coder

// Type Definitions
namespace coder {
namespace matlabshared {
namespace autonomous {
namespace internal {
class CircularBuffer {
public:
  CircularBufferIndex *Index;
  short Buffer[2073600];
};

} // namespace internal
} // namespace autonomous
} // namespace matlabshared
} // namespace coder

#endif
//
// File trailer for CircularBuffer.h
//
// [EOF]
//
