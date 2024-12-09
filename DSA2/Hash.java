import java.util.Objects;


public class Hash {
    private Entry[] table;
    private final int capacity;
    private int size;
    private boolean isRehashing = false;



    public Hash(int capacity) {
        this.capacity = capacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    private static class Entry {
        String Name;
        String phoneNumber;
        boolean occupiedBefore;

        public Entry(String Name, String phoneNumber) {
            this.Name = Name;
            this.phoneNumber = phoneNumber;
            this.occupiedBefore = true;
        }
    }

    public static long calc_hash(String key, int table_size) {
        int i, l = key.length();
        long hash = 0;
        for (i = 0; i < l; i++) {
            hash += Character.getNumericValue(key.charAt(i));
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        if ( hash > 0) return hash % table_size;
        else return -hash % table_size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void insert(String name, String phone_number) {
        if (isFull()) {
            System.out.println("Hash table is full");
        } else {
            int index = (int)calc_hash(name, capacity);
            while (table[index] != null) {
                index = (index + 1) % capacity;
            }if(!isRehashing) System.out.println("Contact added, Name: " + name + " ,Number: " + phone_number);
            table[index] = new Entry(name, phone_number);
            size++;
        }
        isRehashing = false;
    }

    public void search(String name) {
        if (isEmpty()) {
            System.out.println("Hash table is empty");
            return;
        }
        int index = (int)calc_hash(name, capacity);
        int i = capacity;
        while ((table[index] != null && table[index].occupiedBefore) && i != 0) {
            if (table[index] != null && Objects.equals(table[index].Name, name)) {
                System.out.println("Contact found!");
                System.out.println("Name: " + table[index].Name + " ,Number: " + table[index].phoneNumber);
                return;
            }
            i-=1;
            index = (index + 1) % capacity;
        }

        System.out.println("Name not found");
    }

    public void remove(String name) {
        int index = (int)calc_hash(name, capacity);
        boolean exist = false;
        int i = capacity;
        while ((table[index] != null && table[index].occupiedBefore) && i != 0) {
            if (Objects.equals(table[index].Name, name)) {
                System.out.println(table[index].Name + " removed from contacts!");
                table[index] = null;
                size--;
                exist = true;
            }
            i -= 1;
            index = (index + 1) % capacity;
        }
        if(!exist) {System.out.println("Contact doesn't exist!");}
        else rehash();
    }

    private void rehash() {
        Entry[] oldTable = table.clone();
        table = new Entry[capacity];
        size = 0;
        for (Entry entry : oldTable) {
            if (entry != null && entry.occupiedBefore) {
                isRehashing = true;
                insert(entry.Name, entry.phoneNumber);
            }
        }
    }


    public void print() {
        if(isEmpty()){
            System.out.println("The is empty");
        }else{
            for (int i = 0; i < capacity; i++) {
                if (table[i] != null) {
                    System.out.println("Name: " + table[i].Name + " ,Number: " + table[i].phoneNumber);

                }
            }
        }
    }

    public void update(String name, String new_phone_number) {
        int index = (int)calc_hash(name, capacity);
        int i = capacity;
        while ((table[index] != null && table[index].occupiedBefore) && i != 0) {
            if (Objects.equals(table[index].Name, name)) {
                table[index].phoneNumber = new_phone_number;
                System.out.println("Contact name: " + table[index].Name + " ,Number updated to: " + table[index].phoneNumber);
                return;
            }
            i-=1;
            index = (index + 1) % capacity;
        }
        System.out.println("Name not found!");
    }
}