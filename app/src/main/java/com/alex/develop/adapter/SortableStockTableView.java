package com.alex.develop.adapter;

import android.content.Context;
import android.util.AttributeSet;
import com.alex.develop.entity.Stock;
import com.alex.develop.stockanalyzer.Analyzer;
import com.alex.develop.stockanalyzer.R;
import java.util.Comparator;
import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;
import de.codecrafters.tableview.toolkit.TableDataRowColorizers;


public class SortableStockTableView extends SortableTableView<Stock> {

    public SortableStockTableView(Context context) {
        this(context, null);
    }

    public SortableStockTableView(Context context, AttributeSet attributes) {
        this(context, attributes, 0);
    }

    public SortableStockTableView(Context context, AttributeSet attributes, int styleAttributes) {
        super(context, attributes, styleAttributes);

        SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(context,
                Analyzer.getContext().getResources().getString( R.string.stock_code),
                Analyzer.getContext().getResources().getString( R.string.stock_price),
                Analyzer.getContext().getResources().getString( R.string.stock_increase)
                ,Analyzer.getContext().getResources().getString( R.string.main_one)
                ,Analyzer.getContext().getResources().getString( R.string.main_twenty)
                ,Analyzer.getContext().getResources().getString( R.string.main_run)
                ,Analyzer.getContext().getResources().getString( R.string.main_big_order)
        );
        simpleTableHeaderAdapter.setTextColor(context.getResources().getColor(R.color.table_header_text));
        setHeaderAdapter(simpleTableHeaderAdapter);

        int rowColorEven = context.getResources().getColor(R.color.table_data_row_even);
        int rowColorOdd = context.getResources().getColor(R.color.table_data_row_odd);
        setDataRowColoriser(TableDataRowColorizers.alternatingRows(rowColorEven, rowColorOdd));
        setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());

        setColumnWeight(0, 1);
        setColumnWeight(1, 1);
        setColumnWeight(2, 1);

        setColumnWeight(3, 1);
        setColumnWeight(4, 1);
        setColumnWeight(5, 1);
        setColumnWeight(6, 1);


        setColumnComparator(0, new StockNameComparator());
        setColumnComparator(1, new StockCloseComparator());
        setColumnComparator(2, new StockChangeComparator());

//        addDataClickListener(new StockClickListener());
    } 

//    private class StockClickListener implements TableDataClickListener<Stock> {
//        @Override
//        public void onDataClicked(int rowIndex, Stock clickedData) {
//            String StockString = " " + clickedData.getName();
//            Toast.makeText(getContext(), StockString, Toast.LENGTH_SHORT).show();
//        }
//    }


    private static class StockNameComparator implements Comparator<Stock> {
        @Override
        public int compare(Stock Stock1, Stock Stock2) {
            return Stock1.getName().compareTo(Stock2.getName());
        }
    }

    private static class StockCloseComparator implements Comparator<Stock> {

        @Override
        public int compare(Stock Stock1, Stock Stock2) {
     
            if ( Stock1.getToday().getClose() < Stock2.getToday().getClose() ) return -1;
            if (Stock1.getToday().getClose()  > Stock2.getToday().getClose() ) return 1;
            return 0;
        }
    }

    private static class StockChangeComparator implements Comparator<Stock> {
        @Override
        public int compare(Stock Stock1, Stock Stock2) {
            if ( Stock1.getToday().getChange() < Stock2.getToday().getChange() ) return -1;
            if (Stock1.getToday().getChange()  > Stock2.getToday().getChange() ) return 1;
            return 0;
        }
    }
    
    

 

}
