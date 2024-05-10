package com.geanbrandao.br.billbuddy.presentation.common

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.geanbrandao.br.billbuddy.R
import com.geanbrandao.br.billbuddy.presentation.common.DragAnchors.Default
import com.geanbrandao.br.billbuddy.presentation.common.DragAnchors.End
import com.geanbrandao.br.billbuddy.presentation.common.DragAnchors.Start
import com.geanbrandao.br.billbuddy.ui.theme.BillBuddyTheme
import com.geanbrandao.br.billbuddy.ui.theme.CornerSizeOne
import com.geanbrandao.br.billbuddy.ui.theme.PaddingFour
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SwipeLeft(
    modifier: Modifier = Modifier,
    onDeleteClicked: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val density = LocalDensity.current
    val maxSwipeWidth = with(density) { 48.dp.toPx() }
    val scope = rememberCoroutineScope()

    val state = remember {
        RevealState(
            initialValue = Default,
            positionalThreshold = { distance: Float -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            animationSpec = tween(),
        )
    }

    SwipeToReveal(
        state = state,
        maxSwipeWidth = maxSwipeWidth,
        directions = setOf(End),
        background = {
            if (state.dismissDirection == End) {
                BackgroundSwipeLeftView(
                    onDeleteClicked = {
                        scope.launch {
                            state.anchoredDraggableState.anchoredDrag {
                                dragTo(0f)
                            }
                        }
                        onDeleteClicked()
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        dismissContent = content,
        modifier = modifier,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@ExperimentalMaterial3Api
fun SwipeToReveal(
    state: RevealState,
    maxSwipeWidth: Float,
    background: @Composable RowScope.() -> Unit,
    dismissContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    directions: Set<DragAnchors> = setOf(Start, End)
) {
    Box(
        modifier
            .anchoredDraggable(
                state = state.anchoredDraggableState,
                orientation = Orientation.Horizontal,
            )
            .onSizeChanged { _ ->
                val newAnchors = DraggableAnchors {
                    Default at 0f
                    if (Start in directions) {
                        Start at maxSwipeWidth
                    }
                    if (End in directions) {
                        End at -maxSwipeWidth
                    }
                }
                state.anchoredDraggableState.updateAnchors(newAnchors)
            }
    ) {
        Row(
            content = background,
            modifier = Modifier.matchParentSize()
        )
        Row(
            content = dismissContent,
            modifier = Modifier.offset { IntOffset(state.requireOffset().roundToInt(), 0) }
        )
    }
}

@Composable
fun BackgroundSwipeLeftView(
    modifier: Modifier = Modifier,
    onDeleteClicked: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.errorContainer,
                shape = RoundedCornerShape(CornerSizeOne),
            )
    ) {
        IconButton(
            onClick = onDeleteClicked,
            modifier = Modifier.align(alignment = CenterEnd),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "Remover item",
                tint = MaterialTheme.colorScheme.onErrorContainer,
            )
        }
    }
}

enum class DragAnchors {
    Default,
    Start,
    End,
}

@OptIn(ExperimentalFoundationApi::class)
class RevealState constructor(
    initialValue: DragAnchors,
    positionalThreshold: (totalDistance: Float) -> Float,
    velocityThreshold: () -> Float,
    animationSpec: AnimationSpec<Float>,
    confirmValueChange: (DragAnchors) -> Boolean = { true },
) {

    internal val anchoredDraggableState = AnchoredDraggableState(
        initialValue = initialValue,
        animationSpec = animationSpec,
        confirmValueChange = confirmValueChange,
        positionalThreshold = positionalThreshold,
        velocityThreshold = velocityThreshold,
    )

    internal val offset: Float get() = anchoredDraggableState.offset

    fun requireOffset(): Float = anchoredDraggableState.requireOffset()

    val dismissDirection: DragAnchors?
        get() = if (offset == 0f || offset.isNaN())
            null
        else if (offset > 0f) Start else End
}

@Preview
@Composable
private fun BackgroundSwipeLeftPreview() {
    BillBuddyTheme {
        BackgroundSwipeLeftView()
    }
}

@Preview
@Composable
private fun SwipeLeftPreview() {
    BillBuddyTheme {
        SwipeLeft(
            onDeleteClicked = { },
            content = {
                Box(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(CornerSizeOne)
                    )
                ) {
                    Text(
                        text = "Teste",
                        Modifier
                            .padding(PaddingFour)
                            .fillMaxWidth()
                    )
                }
            }
        )
    }
}