import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Podaj nazwe pliku, z którego mam pobrać zbiór treningowy");
        Scanner scanner = new Scanner(System.in);
        String trainSetName = scanner.nextLine();
        File trainSetFile = new File(trainSetName+".txt");
        Scanner readFile = new Scanner(trainSetFile);
        ArrayList<Iris> irisList = new ArrayList<>();

        while (readFile.hasNext()) {
            String line = readFile.nextLine();
            String[] partsString = line.split(",");
            Double[] partsDouble = new Double[4];
            for (int i = 0; i < partsString.length - 1; i++) {
                partsDouble[i] = Double.valueOf(partsString[i]);
            }
            Iris newIris = new Iris(partsDouble[0], partsDouble[1], partsDouble[2], partsDouble[3], partsString[4]);
            irisList.add(newIris);
        }
        System.out.println("Podaj nazwe pliku, z ktorego mam pobrac zbior testowy");
        ArrayList<Iris> testIrises = new ArrayList<>();

        String testSetName = scanner.nextLine();
        File testSetFile = new File(testSetName+".txt");
        readFile = new Scanner(testSetFile);
        while (readFile.hasNext()) {
            String line = readFile.nextLine();
            String[] partsString = line.split(",");
            Double[] partsDouble = new Double[4];
            for (int i = 0; i < partsString.length - 1; i++) {
                partsDouble[i] = Double.valueOf(partsString[i]);
            }
            Iris newIris = new Iris(partsDouble[0], partsDouble[1], partsDouble[2], partsDouble[3], partsString[4]);
            testIrises.add(newIris);
        }
        System.out.println("Podaj liczbe K");
        ArrayList<DistanceAndName> distancesAndNames = new ArrayList<>();
        int k = Integer.parseInt(scanner.nextLine());
        String[] names = new String[k];
        double accuracy=0;
        for (int i = 0; i < testIrises.size(); i++) {
            int setosas = 0;
            int versicolors = 0;
            int virginicas = 0;
            for (int j = 0; j < irisList.size(); j++) {
                distancesAndNames.add(calculatedistance(testIrises.get(i), irisList.get(j)));
            }
            Collections.sort(distancesAndNames, new Comparator<DistanceAndName>() {
                @Override
                public int compare(DistanceAndName o1, DistanceAndName o2) {
                    Double x1 = ((DistanceAndName) o1).distance;
                    Double x2 = ((DistanceAndName) o2).distance;
                    return x1.compareTo(x2);
                }
            });
            for (int j = 0; j < k; j++) {
                names[j] = distancesAndNames.get(j).name;
            }
            for (int j = 0; j < names.length; j++) {
                if (Objects.equals(names[j], "Iris-setosa"))
                    setosas++;
                if (Objects.equals(names[j], "Iris-versicolor"))
                    versicolors++;
                if (Objects.equals(names[j], "Iris-virginica"))
                    virginicas++;
            }
            int biggest=setosas;
            if (versicolors >biggest)
                biggest=versicolors;
            if (virginicas>biggest)
                biggest=virginicas;

            String speciment="";
            if (setosas==biggest)
                speciment="Iris-setosa";
            if (versicolors==biggest)
                speciment="Iris-versicolor";
            if (virginicas==biggest)
                speciment="Iris-virginica";

            String ifTrue="falsz, poniewaz jest to "+testIrises.get(i).name;
            if (Objects.equals(testIrises.get(i).name, speciment))
                ifTrue="prawda";

            System.out.println("Irys o podanych wymiarach należy do gatunku " +speciment+ " i jest to "+ifTrue);

            if (ifTrue=="prawda"){
                accuracy++;
            }

            distancesAndNames.clear();
        }

        System.out.println("Dokladnosc AI wynosi: "+(accuracy/testIrises.size())*100+"%");
        while(true) {
            int setosas = 0;
            int versicolors = 0;
            int virginicas = 0;
            System.out.println("Podaj wektor ktory chcesz zakwalifikować.Wpisz \"wyjdz\" aby zakonczyc dzialanie programu: ");
            String newIris = scanner.nextLine();
            if (Objects.equals(newIris, "wyjdz"))
                System.exit(0);
            String parts[] = newIris.split(",");
            Iris scanneredIris = new Iris(Double.valueOf(parts[0]), Double.valueOf(parts[1]), Double.valueOf(parts[2]), Double.valueOf(parts[3]), parts[4]);
            for (int i = 0; i < irisList.size(); i++) {
                distancesAndNames.add(calculatedistance(scanneredIris, irisList.get(i)));
            }
            Collections.sort(distancesAndNames, new Comparator<DistanceAndName>() {
                @Override
                public int compare(DistanceAndName o1, DistanceAndName o2) {
                    Double x1 = ((DistanceAndName) o1).distance;
                    Double x2 = ((DistanceAndName) o2).distance;
                    return x1.compareTo(x2);
                }
            });
            for (int j = 0; j < k; j++) {
                names[j] = distancesAndNames.get(j).name;
            }
            for (int j = 0; j < names.length; j++) {
                if (Objects.equals(names[j], "Iris-setosa"))
                    setosas++;
                if (Objects.equals(names[j], "Iris-versicolor"))
                    versicolors++;
                if (Objects.equals(names[j], "Iris-virginica"))
                    virginicas++;
            }
            int biggest = setosas;
            if (versicolors > biggest)
                biggest = versicolors;
            if (virginicas > biggest)
                biggest = virginicas;

            String speciment = "";
            if (setosas == biggest)
                speciment = "Iris-setosa";
            if (versicolors == biggest)
                speciment = "Iris-versicolor";
            if (virginicas == biggest)
                speciment = "Iris-virginica";

            String ifTrue = "falsz, poniewaz jest to " + scanneredIris.name;
            if (Objects.equals(scanneredIris.name, speciment))
                ifTrue = "prawda";

            System.out.println("Irys o podanych wymiarach należy do gatunku " + speciment + " i jest to " + ifTrue);
        }
    }



    static DistanceAndName calculatedistance(Iris test, Iris train) {
        double powerDistance = Math.pow((train.first - test.first), 2) + Math.pow((train.second - test.second), 2) + Math.pow((train.third - test.third), 2) + Math.pow((train.fourth - test.fourth), 2);
        DistanceAndName result = new DistanceAndName(powerDistance, train.name);
        return result;
    }


}
