package traffic.csv.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import here.traffic.flow.api.Lot;

public class LotTrafficInfo {
	
	
	private Lot lot;
	
	private String dateTime;
	
	private int includedFiINRadius_1 = 0;
	private int includedFiOUTRadius_1 = 0;
	
	private int includedFiINRadius_2 = 0;
	private int includedFiOUTRadius_2 = 0;
	
	private int includedFiINRadius_3 = 0;
	private int includedFiOUTRadius_3 = 0;
	
	private BigDecimal totalJamINRadius_1 = new BigDecimal(0.00);
	private BigDecimal totalJamOUTRadius_1 = new BigDecimal(0.00);
	
	private BigDecimal totalJamINRadius_2 = new BigDecimal(0.00);
	private BigDecimal totalJamOUTRadius_2 = new BigDecimal(0.00);
	
	private BigDecimal totalJamINRadius_3 = new BigDecimal(0.00);
	private BigDecimal totalJamOUTRadius_3 = new BigDecimal(0.00);

	public LotTrafficInfo(Lot lot, String dateTime) {
		super();
		this.lot = lot;
		this.dateTime = dateTime;
	}
	
	public BigDecimal getAvgINForRadius_1(){
		BigDecimal avg = new BigDecimal(0.00);
		if (this.includedFiINRadius_1>0){
			avg = this.totalJamINRadius_1.divide(new BigDecimal(this.includedFiINRadius_1), 2,
				RoundingMode.HALF_UP);
		}
		return avg;
	}
	
	public BigDecimal getAvgOUTForRadius_1(){
		BigDecimal avg = new BigDecimal(0.00);
		if(this.includedFiOUTRadius_1>0){
			avg = this.totalJamOUTRadius_1.divide(new BigDecimal(this.includedFiOUTRadius_1), 2,
				RoundingMode.HALF_UP);
		}
		return avg;
	}
	
	public BigDecimal getAvgINForRadius_2(){
		BigDecimal avg = new BigDecimal(0.00);
		if (this.includedFiINRadius_2>0){
			avg = this.totalJamINRadius_2.divide(new BigDecimal(this.includedFiINRadius_2), 2,
				RoundingMode.HALF_UP);
		}
		return avg;
	}
	
	public BigDecimal getAvgOUTForRadius_2(){
		BigDecimal avg = new BigDecimal(0.00);
		if(this.includedFiOUTRadius_2>0){
			avg = this.totalJamOUTRadius_2.divide(new BigDecimal(this.includedFiOUTRadius_2), 2,
				RoundingMode.HALF_UP);
		}
		return avg;
	}
	
	public BigDecimal getAvgINForRadius_3(){
		BigDecimal avg = new BigDecimal(0.00);
		if (this.includedFiINRadius_3>0){
			avg = this.totalJamINRadius_3.divide(new BigDecimal(this.includedFiINRadius_3), 2,
				RoundingMode.HALF_UP);
		}
		return avg;
	}
	
	public BigDecimal getAvgOUTForRadius_3(){
		BigDecimal avg = new BigDecimal(0.00);
		if(this.includedFiOUTRadius_3>0){
			avg = this.totalJamOUTRadius_3.divide(new BigDecimal(this.includedFiOUTRadius_3), 2,
				RoundingMode.HALF_UP);
		}
		return avg;
	}
	
	public void addJamForTotalJamINRadius_1 (BigDecimal jam){
		this.totalJamINRadius_1 = this.totalJamINRadius_1.add(jam);
	}
	
	public void addJamForTotalJamOUTRadius_1 (BigDecimal jam){
		this.totalJamOUTRadius_1 = this.totalJamOUTRadius_1.add(jam); 
	}
	
	public void addJamForTotalJamINRadius_2 (BigDecimal jam){
		this.totalJamINRadius_2 = this.totalJamINRadius_2.add(jam);
	}
	
	public void addJamForTotalJamOUTRadius_2 (BigDecimal jam){
		this.totalJamOUTRadius_2 = this.totalJamOUTRadius_2.add(jam); 
	}
	
	public void addJamForTotalJamINRadius_3 (BigDecimal jam){
		this.totalJamINRadius_3 = this.totalJamINRadius_3.add(jam);
	}
	
	public void addJamForTotalJamOUTRadius_3 (BigDecimal jam){
		this.totalJamOUTRadius_3 = this.totalJamOUTRadius_3.add(jam); 
	}
	
	public void incrementIncludedFiINRadius_1 (){
		this.includedFiINRadius_1++;
	}
	
	public void incrementIncludedFiOUTRadius_1 (){
		this.includedFiOUTRadius_1++;
	}
	
	public void incrementIncludedFiINRadius_2 (){
		this.includedFiINRadius_2++;
	}
	
	public void incrementIncludedFiOUTRadius_2 (){
		this.includedFiOUTRadius_2++;
	}
	
	public void incrementIncludedFiINRadius_3 (){
		this.includedFiINRadius_3++;
	}
	
	public void incrementIncludedFiOUTRadius_3 (){
		this.includedFiOUTRadius_3++;
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	public int getIncludedFiINRadius_1() {
		return includedFiINRadius_1;
	}

	public int getIncludedFiOUTRadius_1() {
		return includedFiOUTRadius_1;
	}

	public int getIncludedFiINRadius_2() {
		return includedFiINRadius_2;
	}

	public int getIncludedFiOUTRadius_2() {
		return includedFiOUTRadius_2;
	}

	public int getIncludedFiINRadius_3() {
		return includedFiINRadius_3;
	}

	public int getIncludedFiOUTRadius_3() {
		return includedFiOUTRadius_3;
	}

	public BigDecimal getTotalJamINRadius_1() {
		return totalJamINRadius_1.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getTotalJamOUTRadius_1() {
		return totalJamOUTRadius_1.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getTotalJamINRadius_2() {
		return totalJamINRadius_2.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getTotalJamOUTRadius_2() {
		return totalJamOUTRadius_2.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getTotalJamINRadius_3() {
		return totalJamINRadius_3.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getTotalJamOUTRadius_3() {
		return totalJamOUTRadius_3.setScale(2, RoundingMode.HALF_EVEN);
	}

	public String getDateTime() {
		return dateTime;
	}	
	
}
