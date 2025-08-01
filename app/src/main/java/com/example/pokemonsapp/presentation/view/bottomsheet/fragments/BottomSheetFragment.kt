package com.example.pokemonsapp.presentation.view.bottomsheet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import com.example.pokemonsapp.data.api.models.ValidatedAbilityDetail
import com.example.pokemonsapp.databinding.FragmentBottomSheetBinding
import com.example.pokemonsapp.presentation.GlobalConstants.ARG_ABILITY_DETAIL
import com.example.pokemonsapp.presentation.view.BaseBottomSheetFragment

class BottomSheetFragment : BaseBottomSheetFragment<FragmentBottomSheetBinding>() {

    companion object {
        fun newInstance(abilityDetail: ValidatedAbilityDetail): BottomSheetFragment {
            return BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ABILITY_DETAIL, abilityDetail)
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()

        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val layoutParams = it.layoutParams
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            it.layoutParams = layoutParams


        }
    }
    private val abilityDetail: ValidatedAbilityDetail? by lazy {
        arguments?.getParcelable(ARG_ABILITY_DETAIL)
    }

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentBottomSheetBinding {
        return FragmentBottomSheetBinding.inflate(inflater, container, attachToParent)
    }

    override fun initUi(systemBars: Insets) {
        abilityDetail?.let { ability ->
            binding.apply {
                bottomSheetTitle.text = ability.name.replaceFirstChar{ char -> char.uppercase() }
                bottomSheetBody.text = ability.effect
            }
        } ?: run {
            dismiss()
        }
    }
}
