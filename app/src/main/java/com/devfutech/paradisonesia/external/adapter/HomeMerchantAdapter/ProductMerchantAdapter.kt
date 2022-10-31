package com.devfutech.paradisonesia.external.adapter.HomeMerchantAdapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devfutech.paradisonesia.R
import com.devfutech.paradisonesia.databinding.ItemMerchantTransactionBinding
import com.devfutech.paradisonesia.domain.model.advance_filter.AdvanceFilter
import com.devfutech.paradisonesia.domain.model.merchant.homeMerchant.HomeMerchant
import com.devfutech.paradisonesia.external.extension.visible
import com.devfutech.paradisonesia.external.utils.FileUtils
import com.devfutech.paradisonesia.presentation.MainActivity
import com.devfutech.paradisonesia.presentation.bottomsheet.filter.FilterBottomSheet
import com.devfutech.paradisonesia.presentation.dialog.DialogMessageFragment
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import timber.log.Timber


class ProductMerchantAdapter(
    private val list: MutableList<HomeMerchant.MerchantProduct>, private val activity: Activity
) : RecyclerView.Adapter<ProductMerchantAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_merchant_list_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitleProductMerchant.text = list.get(position).productName
        holder.tvProductServiceName.text = list.get(position).productType
        holder.tvProductAddress.text = list.get(position).productAddress
        holder.tvTotal.text = holder.itemView.context.getString(R.string.final_price, FileUtils.convertToCurrency(list.get(position).productTotal))
        val time = FileUtils.dateToCalendar(list.get(position).productDate!!).get(0)
        val tgl = FileUtils.dateToCalendar(list.get(position).productDate!!).get(1)
        val month = FileUtils.dateToCalendar(list.get(position).productDate!!).get(2)
        holder.tvItemClock.text = time
        holder.tvItemDate.text = tgl
        holder.tvItemMonth.text = month

        holder.llCalendar.setOnClickListener{

            if(holder.llDate.visibility==View.INVISIBLE) {
                holder.llDate.visibility = View.VISIBLE
                //loading our custom made animations

                val animation =
                    AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fade_in)
                //starting the animation

                holder.llDate.startAnimation(animation)
            }
            else{
                holder.llDate.visibility = View.INVISIBLE
                //loading our custom made animations

                val animation =
                    AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fade_out)
                //starting the animation

                holder.llDate.startAnimation(animation)
            }
        }

        holder.itemView.setOnClickListener{
            (activity as MainActivity).showDialogMessage()
            Timber.tag("ProductMerchantAdapter").d("COXKNVV")
        }
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvTitleProductMerchant : AppCompatTextView = itemView.findViewById(R.id.tv_item_title)
        val tvProductServiceName : AppCompatTextView = itemView.findViewById(R.id.tv_service_name)
        val tvProductAddress : AppCompatTextView = itemView.findViewById(R.id.tv_address)
        val tvTotal : AppCompatTextView = itemView.findViewById(R.id.tv_total)
        val llDate : LinearLayoutCompat = itemView.findViewById(R.id.ll_date)
        val llCalendar : LinearLayoutCompat = itemView.findViewById(R.id.ll_calendar)
        val tvItemClock : AppCompatTextView = itemView.findViewById(R.id.tv_item_clock)
        val tvItemDate : AppCompatTextView = itemView.findViewById(R.id.tv_item_date)
        val tvItemMonth : AppCompatTextView = itemView.findViewById(R.id.tv_item_month)
    }

    override fun getItemCount(): Int {

        return list.size
    }
}