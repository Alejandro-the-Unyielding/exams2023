package e1;

import java.util.List;

public class WindowingFactoryImpl implements WindowingFactory{

    @Override
    public <X> Windowing<X, X> trivial() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'trivial'");
    }

    @Override
    public <X> Windowing<X, Pair<X, X>> pairing() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pairing'");
    }

    @Override
    public Windowing<Integer, Integer> sumLastFour() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sumLastFour'");
    }

    @Override
    public <X> Windowing<X, List<X>> lastN(int n) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lastN'");
    }

    @Override
    public Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lastWhoseSumIsAtLeast'");
    }

}
