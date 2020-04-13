class Calendar{

    public static final int WEEKDAYS = 7;

    public static void main(String[] args){

        String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String giorni = "MON  TUE  WED  THU  FRI  SAT  SUN";
        String blanks = "";
        int j = 0;

        System.out.println();
        for(int i = 0; i < month.length; i++){
            System.out.println("          " + month[i] + "\r\n");
            System.out.println(giorni + "\r\n");
            int daysInMonth;

            switch (i){
                case 1:
                    daysInMonth = 28;
                    break;
                case 3:
                case 5:
                case 8:
                case 10: 
                    daysInMonth = 30;
                    break;
                default:
                    daysInMonth = 31;
                    break;                
            }

            switch (j){
                    case 0:
                    case 1: blanks = "     ";
                            break;
                    case 2: blanks = "          ";
                            break;
                    case 3: blanks = "               ";
                            break;
                    case 4: blanks = "                    ";
                            break;
                    case 5: blanks = "                         ";
                            break;
                    case 6: blanks = "                              ";
                            break;
                    default: break;
            }

            System.out.print(blanks);
            int n = 1;
            do{ 
                do{
                    System.out.print(' ');
                    if(n < 10) 
                        System.out.print(' ');
                    System.out.print(n + "  ");

                    j++;
                    n++;   
                }while((j < WEEKDAYS)&&(n != daysInMonth+1));
            
                System.out.println("\r\n");
                j = 0;
            }while(n <= daysInMonth);
        }
    }
}