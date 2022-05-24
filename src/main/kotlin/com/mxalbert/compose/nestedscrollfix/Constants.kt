package com.mxalbert.compose.nestedscrollfix

import org.objectweb.asm.Type
import org.objectweb.asm.commons.Method

// Class names
internal const val VelocityTrackerClass = "androidx.compose.ui.input.pointer.util.VelocityTracker"
internal const val ScrollingLogicClass = "androidx.compose.foundation.gestures.ScrollingLogic"

// Types
internal val Offset = Type.getObjectType("androidx/compose/ui/geometry/Offset")
internal val OffsetCompanion = Type.getObjectType("androidx/compose/ui/geometry/Offset\$Companion")
internal val VelocityTracker =
    Type.getObjectType("androidx/compose/ui/input/pointer/util/VelocityTracker")

// Methods
internal val Offset_Zero = Method.getMethod("long getZero-F1C5BW0 ()")
internal val Offset_plus = Method.getMethod("long plus-MK-Hz9U (long, long)")
internal val Offset_minus = Method.getMethod("long minus-MK-Hz9U (long, long)")
internal val VelocityTracker_addPositionChange =
    Method.getMethod("void addPositionChange-Uv8p0NA (long, long)")
