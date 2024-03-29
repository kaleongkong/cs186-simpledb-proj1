package simpledb;

import java.io.Serializable;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

    /**
     * A help class to facilitate organizing the information of each field
     * */
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        Type fieldType;
        
        /**
         * The name of the field
         * */
        String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }
    TDItem[] td_items;
    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() {
        // some code goes here
        return Arrays.asList(td_items).iterator();
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        // some code goes here
		try{
		    int len = typeAr.length;
		    td_items = new TDItem[len];
		    for (int i =0; i< len; i++){
				TDItem item= new TDItem(typeAr[i], fieldAr[i]);
				td_items[i]=item;
		    }
		}catch(Exception e){
		    System.out.println(e);
		}
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     * 
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
		try{
		    int len = typeAr.length;
		    td_items = new TDItem[len];
		    for (int i =0; i< len; i++){
			TDItem item= new TDItem(typeAr[i], null);
			td_items[i] = item;
		    }
		}catch(Exception e){
		    System.out.println(e);
		}
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        // some code goes here
        return td_items.length;
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     * 
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
	// some code goes here
        if(i<0 || i>=this.numFields()){
	    throw new NoSuchElementException();
	}
        return td_items[i].fieldName;
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     * 
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
        // some code goes here
        if(i<0 || i>=this.numFields()){
        	throw new NoSuchElementException();
        }
        return td_items[i].fieldType;
    }

    /**
     * Find the index of the field with a given name.
     * 
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
        // some code goes here
	for (int i=0; i<this.numFields() ; i++){
	    if(this.getFieldName(i)!=null && this.getFieldName(i).equals(name)){
		return i;
	    }
	}
        throw new NoSuchElementException();
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        // some code goes here
	int size = 0;
	for(int i=0; i<this.numFields() ; i++){
	    if(this.getFieldType(i) == Type.INT_TYPE){
		size = size + Type.INT_TYPE.getLen();
	    }else if (this.getFieldType(i) == Type.STRING_TYPE){
		size = size + Type.STRING_TYPE.getLen();
	    }
	}
        return size;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
        // some code goes here
	int new_len = td1.numFields()+td2.numFields();
	int td1_len = td1.numFields();
	int td2_len = td2.numFields();
	Type[] typeAr = new Type[new_len];
	String[] fieldAr = new String[new_len];
	for(int i = 0; i< new_len; i++){
	    if (td1_len!=0){
	        typeAr[i] = td1.getFieldType(i);
		fieldAr[i] = td1.getFieldName(i);
		td1_len--;
	    }else{
		typeAr[i] = td2.getFieldType(i-td1.numFields());
		fieldAr[i] = td2.getFieldName(i-td1.numFields());
		td2_len--;
	    }
	}
	
        return new TupleDesc(typeAr, fieldAr);
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they are the same size and if the n-th
     * type in this TupleDesc is equal to the n-th type in td.
     * 
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
        // some code goes here
		if (o == null){
		    return false;
		}
		try{
		    TupleDesc t = (TupleDesc) o;
		    if(this.numFields() != t.numFields()){
			return false;
		    }
		    for (int i =0; i<this.numFields(); i++){
			if(!t.getFieldType(i).equals(this.getFieldType(i))){
			    return false;
		        }
		    }
		}catch(Exception e){
		    return false;
		}
	
        return true;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * 
     * @return String describing this descriptor.
     */
    public String toString() {
	String result = "";
        for(int i=0; i<this.numFields(); i++){
	    result = this.getFieldType(i)+"("+this.getFieldName(i)+")";
	    if(i<this.numFields()-1){
		result = result+",";
	    }
	}
        return result;
    }
}
