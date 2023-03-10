package es.uji.al415617;

import java.util.*;

public class Kmeans {

    int numClusters;
    int numIterations;
    long seed;

    Map<Integer, List<Row>> clusters;
    List<Row> centroides;

    public Kmeans(int numClusters, int numIterations, long seed){
        this.numClusters=numClusters;
        this.numIterations=numIterations;
        this.seed=seed;
    }


    public void train(Table datos){
        Random random = new Random(this.seed);
        this.centroides = createCentroides(datos, random);
        this.clusters = createClusters(datos, centroides);

    }

    public List<Row> createCentroides(Table datos, Random random){
        List<Row> centroides = new ArrayList<>();
        Set<Integer> indicesRandomizados = new HashSet<>();
        for(int contador=0; contador<3; contador++){
            int indiceRandomizado = random.nextInt(datos.filas.size());
            indicesRandomizados.add(indiceRandomizado);
            while (indicesRandomizados.contains(indiceRandomizado))
                indiceRandomizado = random.nextInt(datos.filas.size());

            Row centroide = datos.getRowAt(indiceRandomizado);
            centroides.add(centroide);
        }
        return centroides;
    }

    public Map<Integer, List<Row>> createClusters(Table datos, List<Row> centroides){
        clusters=new HashMap<>();
        for(int indice=0; indice<3; indice++)
            clusters.put(indice, new ArrayList<>());
        for(int indiceRow=0; indiceRow<datos.filas.size(); indiceRow++){
            double distMin=-1;
            int indiceCentroide=-1;
            for(int indiceCen = 0; indiceCen < centroides.size(); indiceCen++) {
                double distEuclidea = new KNN().distanciaEuclidea((RowWithLabel) centroides.get(indiceCen), datos.filas.get(indiceRow).data);
                if(distMin==-1){
                    distMin=distEuclidea;
                    indiceCentroide=indiceCen;
                } else if(distEuclidea<=distMin){
                    distMin=distEuclidea;
                    indiceCentroide=indiceCen;
                }
            }
            clusters.get(indiceCentroide).add(datos.getRowAt(indiceRow));
        }
        return clusters;
    }


}
