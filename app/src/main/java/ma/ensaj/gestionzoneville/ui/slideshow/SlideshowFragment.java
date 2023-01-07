package ma.ensaj.gestionzoneville.ui.slideshow;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ma.ensaj.gestionzoneville.R;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ma.ensaj.gestionzoneville.Service.PHService;
import ma.ensaj.gestionzoneville.beans.PharmacieGard;
import ma.ensaj.gestionzoneville.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    final Calendar myCalendar= Calendar.getInstance();
    final Calendar myCalendar2= Calendar.getInstance();
    PHService phService;
    EditText editText,editText2;
    Button button;
    AutoCompleteTextView editTextFilledExposedDropdown;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES="session";
    public View onCreateView(@NotNull  LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        View root = binding.getRoot();
        phService=new PHService();
        editText=binding.Birthday;
        editText2=binding.Birthday2;
        button=binding.button5;
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        DatePickerDialog.OnDateSetListener date2 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH,month);
                myCalendar2.set(Calendar.DAY_OF_MONTH,day);
                updateLabel2();
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),date2,myCalendar2.get(Calendar.YEAR),myCalendar2.get(Calendar.MONTH),myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(editText.getText()+"    "+editText2.getText());
                String gard=editTextFilledExposedDropdown.getText().toString();
                int idg;
                if(gard.equals("Jour")){
                    idg=1;
                }else{
                    idg=2;
                }
                String id2 = sharedpreferences.getString("id_phar",null);
                //System.out.println(new PharmacieGard(editText.getText().toString(),editText2.getText().toString(),1));
                phService.savePH(new PharmacieGard(Integer.parseInt(id2),editText.getText().toString(),editText2.getText().toString(),idg));
                Toast.makeText(getContext(),"Gard Saved",Toast.LENGTH_LONG);
            }
        });
        ArrayList<String> type = new ArrayList<>();
        type.add("Jour");
        type.add("Nuit");


        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getActivity(),
                        R.layout.dropdown_menu_popup_item,
                        type);

         editTextFilledExposedDropdown = binding.dropdown;
        editTextFilledExposedDropdown.setAdapter(adapter);




      //  final TextView textView = binding.textSlideshow;
       // slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    private void updateLabel(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRENCH);
        editText.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void updateLabel2(){
        String myFormat="yyyy-MM-dd";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRENCH);
        editText2.setText(dateFormat.format(myCalendar2.getTime()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}