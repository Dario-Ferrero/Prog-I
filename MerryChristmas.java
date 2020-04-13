public class MerryChristmas {

    public static void main(String[] args)
	{
        int dx = 38, sx = 38;
        System.out.println();

        for (; dx <= 72 && 3 <= sx; dx++, sx--) {
            for (int j = 0; j <= 75; j++)
				System.out.print(sx <= j && j <= dx ? "*" : " ");
            System.out.println();
        }            
        
        dx = 45;
        sx = 31;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j <= 75; j++)
                System.out.print(sx <= j && j <= dx ? "|" : " ");
            System.out.println();
        }
    }

}