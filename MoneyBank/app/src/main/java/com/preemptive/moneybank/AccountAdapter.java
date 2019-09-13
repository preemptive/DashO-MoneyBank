package com.preemptive.moneybank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.preemptive.moneybank.data.model.Account;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private List<Account> accounts;

    public AccountAdapter(List<Account> accounts) {
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.accountNumberView.setText(account.getAccountNumber());
        holder.balanceView.setText("$" + account.getBalance());
        holder.transferButton.setText("Transfer");
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view)  {
            super(view);
            accountNumberView = view.findViewById(R.id.account_number);
            balanceView = view.findViewById(R.id.account_balance);
            transferButton = view.findViewById(R.id.transfer_button);
        }

        public TextView accountNumberView;
        public TextView balanceView;
        public Button transferButton;
    }
}
