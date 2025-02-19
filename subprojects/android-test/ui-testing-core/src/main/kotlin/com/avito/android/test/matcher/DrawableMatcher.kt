package com.avito.android.test.matcher

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.test.espresso.matcher.BoundedMatcher
import com.avito.android.test.util.getResourceName
import com.avito.android.test.util.matchDrawable
import org.hamcrest.Description

open class DrawableMatcher<T : View>(
    private val drawableSupplier: (T) -> Drawable?,
    private val drawableTintSupplier: (T) -> ColorStateList?,
    @DrawableRes private val src: Int? = null,
    @ColorRes private val tint: Int? = null,
    clazz: Class<out T>
) : BoundedMatcher<View, T>(clazz) {

    private var description: String? = null

    override fun describeTo(description: Description) {
        description.appendText("has compound drawables: ${this.description}")
    }

    override fun matchesSafely(item: T): Boolean {
        val context = item.context
        this.description = getDescription(context.resources)

        val tintColor = if (tint == null) {
            null
        } else {
            ContextCompat.getColor(context, tint)
        }

        return matchDrawable(
            context,
            source = drawableSupplier.invoke(item),
            sourceTint = drawableTintSupplier.invoke(item),
            otherId = src,
            otherTint = tintColor
        )
    }

    private fun getDescription(resources: Resources): String {
        return "drawable=${src.getResourceName(resources)}"
    }
}
