package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView vName = findViewById(R.id.name_tv);
        TextView vAlsoKnownAs = findViewById(R.id.also_known_tv);
        TextView vIngredients = findViewById(R.id.ingredients_tv);
        TextView vOrigin = findViewById(R.id.origin_tv);
        TextView vDescription = findViewById(R.id.description_tv);
        vName.setText(handleMissing(sandwich.getMainName()));
        vOrigin.setText(handleMissing(sandwich.getPlaceOfOrigin()));
        vDescription.setText(handleMissing(sandwich.getDescription()));


        List<String> mAlsoKnows = sandwich.getAlsoKnownAs();
        String output = "";
        for (String s: mAlsoKnows) {
            output += s + ", ";
        }
        if (output.length() > 0){
            output = output.substring(0, output.length() - 2);
        }
        vAlsoKnownAs.setText(handleMissing(output));

        output = "";
        List<String> mIngredientsList = sandwich.getIngredients();
        for (String s: mIngredientsList) {
            output += s + "\n";
        }
        vIngredients.setText(handleMissing(output));
    }
    private String handleMissing(String sError){
        if (sError.equals("") || sError==null){
            return getString(R.string.detail_error_message);
        } else {
            return sError;
        }
    }
}
