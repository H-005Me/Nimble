//package com.example.nimble.RestaurantPages
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.NavHostFragment.findNavController
//import androidx.navigation.fragment.findNavController
//import com.example.nimble.R
//import com.example.nimble.databinding.FragmentSecondBinding
//
///**
// * A simple [Fragment] subclass as the second destination in the navigation.
// */
//class SecondFragment : Fragment() {
//
//private var _binding: FragmentSecondBinding? = null
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//      _binding = FragmentSecondBinding.inflate(inflater, container, false)
//      return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
//    }
//override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}