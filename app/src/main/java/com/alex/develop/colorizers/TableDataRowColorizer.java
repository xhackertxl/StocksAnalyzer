package com.alex.develop.colorizers;

/**
 * A interface for a table data row background color provider. This enables easy coloring of
 * the rows of a {@link com.alex.develop.TableView}.
 */
public interface TableDataRowColorizer<T> {

    /**
     * Gives the row color for the row with the given index holding the given data.
     *
     * @param rowIndex
     *         The index of the row to return the background color for.
     * @param rowData
     *         The data presented in the row to return the background color for.
     * @return The background color that shall be used for the given row.
     */
    int getRowColor(int rowIndex, T rowData);

}
