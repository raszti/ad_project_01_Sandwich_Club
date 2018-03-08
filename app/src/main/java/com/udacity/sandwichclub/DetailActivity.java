package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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

    TextView alsoKnownAsText;
    TextView ingredientsText;
    TextView descriptionText;
    TextView originText;

    ImageView sandwichIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        alsoKnownAsText = findViewById(R.id.also_known_tv);
        ingredientsText = findViewById(R.id.ingredients_tv);
        descriptionText = findViewById(R.id.description_tv);
        originText = findViewById(R.id.origin_tv);

        sandwichIv = findViewById(R.id.image_iv);

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
                .into(sandwichIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        List<String> aliasList = sandwich.getAlsoKnownAs();
        StringBuilder aliasString = new StringBuilder();

        for (int i = 0; i < aliasList.size(); i++) {
            aliasString.append(aliasList.get(i));

            if (i + 1 < aliasList.size()) {
                aliasString.append("\n");
            }
        }

        alsoKnownAsText.setText(aliasString.toString());

        List<String> ingredientsList = sandwich.getIngredients();
        StringBuilder ingredientsString = new StringBuilder();

        for (int i = 0; i < ingredientsList.size(); i++) {
            ingredientsString.append(ingredientsList.get(i));

            if (i + 1 < ingredientsList.size()) {
                ingredientsString.append("\n");
            }
        }

        ingredientsText.setText(ingredientsString.toString());

        descriptionText.setText(sandwich.getDescription());
        originText.setText(sandwich.getPlaceOfOrigin());
    }
}
