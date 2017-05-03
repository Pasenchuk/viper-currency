package com.sbt.currency.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sbt.currency.R;
import com.sbt.currency.domain.DisplayCurrency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasenchuk Victor on 30/04/2017
 */

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.CurrencyHolder> {

    List<DisplayCurrency> displayCurrencies = new ArrayList<>();

    public CurrenciesAdapter() {
        setHasStableIds(true);
    }

    @Override
    public CurrencyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_currency, parent, false);

        return new CurrencyHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyHolder holder, int position) {
        final DisplayCurrency displayCurrency = displayCurrencies.get(position);

        holder.charCode.setText(displayCurrency.getCharCode());
        holder.nominal.setText(displayCurrency.getNominal());
        holder.name.setText(displayCurrency.getName());
        holder.exchangeValue.setText(displayCurrency.getDisplayNominalValue());
        holder.primaryCharCode.setText(displayCurrency.getPrimaryCharCode());
    }

    @Override
    public long getItemId(int position) {
        return displayCurrencies.get(position).getNumCode();
    }

    @Override
    public int getItemCount() {
        return displayCurrencies.size();
    }

    public void setDisplayCurrencies(List<DisplayCurrency> displayCurrencies) {
        this.displayCurrencies = displayCurrencies;
        notifyDataSetChanged();
    }

    class CurrencyHolder extends RecyclerView.ViewHolder {

        TextView charCode;
        TextView nominal;
        TextView name;
        TextView exchangeValue;
        TextView primaryCharCode;

        CurrencyHolder(View itemView) {
            super(itemView);

            charCode = (TextView) itemView.findViewById(R.id.char_code);
            nominal = (TextView) itemView.findViewById(R.id.nominal);
            name = (TextView) itemView.findViewById(R.id.name);
            exchangeValue = (TextView) itemView.findViewById(R.id.exchange_value);
            primaryCharCode = (TextView) itemView.findViewById(R.id.primary_char_code);
        }
    }

}
