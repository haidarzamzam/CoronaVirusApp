package com.haidev.coronavirusapp.ui.screen.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.haidev.coronavirusapp.R

class OnboardingAdapter(private val context: Context, private val item: List<ItemOnboardingModel>) :
    PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return item.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater?.inflate(R.layout.item_onboarding, null)

        val ivOnboarding = view?.findViewById<View>(R.id.iv_onboarding) as ImageView
        ivOnboarding.setBackgroundResource(item[position].img)

        val tvOnboardingTitle = view.findViewById<View>(R.id.tv_onboarding_title) as TextView
        tvOnboardingTitle.text = context.resources.getString(item[position].title)

        val tvOnboardingDesc = view.findViewById<View>(R.id.tv_onboarding_desc) as TextView
        tvOnboardingDesc.text = context.resources.getString(item[position].desc)

        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}