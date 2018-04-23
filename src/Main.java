import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final int KNN = 5;
	private static final int KFOLD = 4;
	private static ArrayList<Integer> classes = new ArrayList();
	public static int maxClassIndex(Integer [] classBasket){
		int classIndex = 0; 
		int classOccurrence = 0;
		for(int i=0; i < classBasket.length; i++){
			if(classBasket[i] > classOccurrence){
				classOccurrence = classBasket[i];
				classIndex = i;
			}
		}
		return classIndex;
	}
	public static SampleRelation calculateDistanceBetweenSamples(Sample sample, Sample sample2){
		SampleRelation relation = new SampleRelation();
		double distance = 0;
		for(int l = 0; l < sample.getAttrs().length; l++){//soma dos quadrados de todos os atributos
			distance += Math.pow(sample.getAttrs()[l]+sample2.getAttrs()[l], 2);
		}
		distance = Math.sqrt(distance); //distancia euclidiana
		
		relation.setTarget(sample2);
		relation.setDistance(distance);
		return relation;
	}
	public static RelationsByFold getRelationsByFoldNumber(Sample sample, int targetFold){
		for(RelationsByFold relationsByFold: sample.getRelationsByFold()){
			if(relationsByFold.getTargetFold() == targetFold){
				return relationsByFold;
			}
		} 
		return null;
	}
	public static Integer getSupposedClassNumber(Sample sample){
		Integer [] classBasket = new Integer [classes.size()];
		for(RelationsByFold relationsByFold : sample.getRelationsByFold()){
			for(int i=0; i < KNN; i++){
				int neighborClassNumber = relationsByFold.getSampleRelationList().get(i).getTarget().getClassNumber();
				classBasket[classes.indexOf(neighborClassNumber)]++; //incrementando ocorrências
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Integer inputSize, attrSize;
		List<Sample> sampleList = new ArrayList();
		Integer totalComparisons = 0, hits = 0;
		
		Scanner sc = new Scanner(System.in);
		try {
			FileReader arq = new FileReader("dados.txt");
			BufferedReader lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			if(linha != null) {
				String[] partes = linha.split(" ");
				if(partes.length == 2){
					inputSize = Integer.parseInt(partes[0]);
					attrSize = Integer.parseInt(partes[1]);
					for(int i = 0; i < inputSize; i++) {
						linha = lerArq.readLine();
						partes = linha.split(" ");
						double attrs[] = new double[attrSize];
						Integer classNumber = null;
						int j = 0;
						for(; j < attrSize; j++) {
							attrs[j] = Double.parseDouble(partes[j]);
						}
						classNumber = Integer.parseInt(partes[j]);
						
						if(!classes.contains(classNumber)){
							classes.add(classNumber);
						}
						
						Sample newSample =  new Sample(attrs, classNumber);
						sampleList.add(newSample);
					}
					int opt = 0;
					while(opt != 10){
						System.out.println("---------------MENU---------------\n"
										 + "1 - Normalizar dados com z-score\n"
										 + "2 - Aplicar k-fold"
										 + "10 - Sair");
						opt = sc.nextInt(); 
						if(opt == 1){
							
						}else if(opt == 2){
							Collections.sort(sampleList);
							for(int i = 0; i < sampleList.size(); i++){
								sampleList.get(i).setFoldNumber(i%KFOLD);
								RelationsByFold [] relations = new RelationsByFold[KFOLD-1];
								for(int j=0; j < KFOLD-1; j++){
									relations[j] = new RelationsByFold();
								}
								sampleList.get(i).setRelationsByFold(relations);
								int relationIndex = 0;
								for(int j = 0; j < KFOLD; j++){
									if(i%KFOLD != j){//setando o número dos folds de teste destino
										sampleList.get(i).getRelationsByFold()[relationIndex].setTargetFold(j);
										relationIndex++;
									}
								}
							}
							for(Sample sample : sampleList){
								for(Sample sample2 : sampleList){
									if(sample.getFoldNumber() != sample2.getFoldNumber()){//fold diferente do teste por amostra
										SampleRelation relation = calculateDistanceBetweenSamples(sample, sample2);
										RelationsByFold relationsByFold = getRelationsByFoldNumber(sample, sample2.getFoldNumber()); // obter fold de treino da amostra 2 dentro da amostra 1
										relationsByFold.getSampleRelationList().add(relation);
									}
								}
								for(RelationsByFold relationsByFold : sample.getRelationsByFold()){//obter vizinhos mais próximos e "classe suposta"
									Integer[] classBasket = new Integer[classes.size()];
									for(int j=0; j<classBasket.length; j++){
										classBasket[j] = 0;
									}
									Collections.sort(relationsByFold.getSampleRelationList()); //ordenar a lista de relações por distancia
									for(int j=0; j < KNN; j++){//obter n-ésimo vizinho
										System.out.print("[ ");
										for(SampleRelation relation : relationsByFold.getSampleRelationList()){
											System.out.print(" "+relation.getTarget().getClassNumber());
										}
										System.out.println("] ");
										
										System.out.print("[");
										for(SampleRelation relation : relationsByFold.getSampleRelationList()){
											System.out.print(" "+relation.getDistance());
										}
										System.out.println("] ");
										
										Integer neighborClass = relationsByFold.getSampleRelationList().get(j).getTarget().getClassNumber();
										classBasket[classes.indexOf(neighborClass)]++;
									}
									//TODO tratar caso de duas classes
									Integer supposedClassByFold  = classes.get(maxClassIndex(classBasket));
									totalComparisons++;
									System.out.println("SC: "+supposedClassByFold+" C:"+sample.getClassNumber() );
									if(supposedClassByFold == sample.getClassNumber()){
										hits++;
									}
								}
							}
							System.out.println("total: "+totalComparisons+"hits: "+hits);
						}
					}
				} else {
					throw new Exception("Tamanho da primeira linha invalido!");
				}
			}

			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
		}
	}

}
