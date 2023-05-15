//
// Sponsored License - for use in support of a program or activity
// sponsored by MathWorks.  Not for government, commercial or other
// non-sponsored organizational use.
// File: occupancyMap.h
//
// MATLAB Coder version            : 5.6
// C/C++ source code generated on  : 15-May-2023 01:38:09
//

#ifndef OCCUPANCYMAP_H
#define OCCUPANCYMAP_H

// Include Files
#include "CircularBuffer.h"
#include "CircularBufferIndex.h"
#include "SharedMapProperties.h"
#include "rtwtypes.h"
#include <cstddef>
#include <cstdlib>

// Type Definitions
namespace coder {
class occupancyMap {
public:
  matlabshared::autonomous::internal::SharedMapProperties SharedProperties;
  matlabshared::autonomous::internal::CircularBufferIndex Index;
  matlabshared::autonomous::internal::CircularBuffer Buffer;
  short DefaultValueInternal;
  boolean_T HasParent;
};

} // namespace coder

#endif
//
// File trailer for occupancyMap.h
//
// [EOF]
//
