package e1;

public class BankAccountFactoryImpl implements BankAccountFactory{

    @Override
    public BankAccount simple() {
        return new AbstractBankAccount(){

            @Override
            protected boolean canDeposit(int amount) {
                if(amount >= 0){
                    return true;
                }
                else{
                    return false;
                }
            }

            @Override
            protected void onDisallowedDeposit() {
            }

            @Override
            protected void onDisallowedWithdraw() {
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance()-amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                if(amount <= balance()){
                    return true;
                }
                else{
                    return false;
                }
            }};

    }

    @Override
    public BankAccount withFee(int fee) {
        return new AbstractBankAccount(){

            @Override
            protected boolean canDeposit(int amount) {
                if(amount >= 0){
                    return true;
                }
                else{
                    return false;
                }
            }

            @Override
            protected void onDisallowedDeposit() {
            }

            @Override
            protected void onDisallowedWithdraw() {
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance()-(amount+fee);
            }

            @Override
            protected boolean canWithdraw(int amount) {
                if((amount+fee) <= balance()){
                    return true;
                }
                else{
                    return false;
                }
            }};
    }

    @Override
    public BankAccount checked() {
        return new AbstractBankAccount(){

            @Override
            protected boolean canDeposit(int amount) {
                return (amount>0 ? true : false);

            }

            @Override
            protected void onDisallowedDeposit() {
                throw new IllegalStateException("Must be a non negative deposit");

            }

            @Override
            protected void onDisallowedWithdraw() {
                throw new IllegalStateException("Withdraw can't be greater than balance");

            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {

                return (balance()-amount >= 0 && amount>0 ? true : false);

            }};
    }

    @Override
    public BankAccount gettingBlocked() {
        return new AbstractBankAccount(){

            private boolean access = true;

            @Override
            protected boolean canDeposit(int amount) {
                return (amount>0 && access ? true : false);

            }

            @Override
            protected void onDisallowedDeposit() {
                this.access = false;
            }

            @Override
            protected void onDisallowedWithdraw() {
                this.access = false;
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {

                return (balance()-amount >= 0 && amount>0 && access ? true : false);

            }};

    }

    @Override
    public BankAccount pool(BankAccount primary, BankAccount secondary) {

        return new BankAccount(){

            private int balance = 0;

            @Override
            public int balance() {
                this.balance = primary.balance() + secondary.balance();
                return this.balance;
            }

            @Override
            public void deposit(int amount) {

                if(primary.balance() > secondary.balance()){
                    secondary.deposit(amount);
                }
                else{
                    primary.deposit(amount);
                }

            }

            @Override
            public boolean withdraw(int amount) {

                return (primary.withdraw(amount) ? true : secondary.withdraw(amount));

            }};

    }

}
