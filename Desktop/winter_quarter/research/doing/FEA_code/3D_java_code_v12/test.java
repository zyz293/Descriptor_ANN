import com.comsol.model.*;
import com.comsol.model.util.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.io.*;
import java.io.File;

import java.math.BigDecimal;

public class Comsol3Dcode {
	private double vf_expt;
	private double ACMode;
	private double CheckOverlap;
	private double DomLength; 
	private double dimension_to_pixel;
	private String ImageType;
	private double CutSide;
	private double TauShift1;
	private double DeltaEpsilonShift1;
	private double TauShift2;
	private double DeltaEpsilonShift2;
	private double ConstEpsilonShift;
	private double InterfaceThickness1;
	private double InterfaceThickness2;
	private double InterfaceThickness;
	private String ep; // it is epmodel.ep
	private String epp; // it is epmodel epp
	private String epint; // it is epmodel.epint
	private String eppint; // it is epmodel eppint
	private double isEpsDistribution;
	private double isReScale;
	private double isGetSolution;
	private double ACbreakdown;
	private double ManualMesh;
	private double MeshLevel;
	private String ControlMode;
	private double tau0;
	private String TimeRange;
	private double CurrentSource;
	private double Compfreq;
	private double AppliedVoltage;
	private double ActualLength;
	private double dimensionY;
	private double dimensionX;
	private double dimensionZ;
	private double electrode1X;
	private double electrode3X;
	private double electrode1Y;
	private double electrode3Y;
	private double extra_E_infi;
	private double MatrixConductivity;
	private double FillerConductivity;
	private double FillerRelPerm;
	private double[] FreqRange;
	private double doubleerfaceRelPerm;
	private double doubleerfaceImagPerm;
	private double ElectrodeConductivity;
	private double ElectrodeRelPerm;
	private double[][] EllipseMatrix;
	private double InterfaceConductivity;
	private double InterfaceRelPerm;
	private double InterfaceImagPerm;
	private double InitialVoltage;
	private int NewClusterNo;
	private double rt_electrode1X;
	private double rt_electrode2X;
	private double rt_electrode3X;
	private double rt_electrode1Y;
	private double rt_electrode2Y;
	private double rt_electrode3Y;
	private double ReScale;
	private double sbimagefile;
	private double NewPosX;
	private double NewPosY;
	private double NewShortAxis;
	private double NewLongAxis;
	private double NewAngle;
	private double GBbr;
	private double GBbs;

	
	private double consttL;
	private double consttvf;                        
	private double consttTauShift1;  				
	private double consttDeltaEpsilonShift1 ;       
	private double consttTauShift2;                
	private double consttDeltaEpsilonShift2;       
	private double consttConstEpsilonShift;
	private double constttau;
	private double consttdimension_to_pixel;
	
	private double IF1mode;
	private double IF2;
	private String PScoeff;
	private String binsource;
	
private static final int DEF_DIV_SCALE = 10;

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();	
	}

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}	
	
	
	
	public static void main(String[] args) throws Exception {
		try {	
		ModelUtil.connect("localhost", 2036);	
		new Comsol3Dcode().run();
		} catch (Exception e) {}
	} 
	
	public Model run() throws Exception {
		try{
		/*****************runmain();***********************/
		double TrialNo = 1;
		/*
		String binsource = "./sample_binary_input_3D_parameter_output.txt";
		consttL = 600;
		consttvf = 1/100;                        
		consttTauShift1 = 1;  				
		consttDeltaEpsilonShift1 = 1;       
		consttTauShift2 = 1;                
		consttDeltaEpsilonShift2 = 1;       
		consttConstEpsilonShift = 0;
		constttau = 0.05;
		consttdimension_to_pixel = 400/240;
		*/
		
		BufferedReader workingfile = new BufferedReader(new FileReader("./workingdir.str"));
		String wdir = workingfile.readLine();
		
		
		BufferedReader polymerfile = new BufferedReader(new FileReader("./polymer.word"));
		String polymer = polymerfile.readLine();
		
		PScoeff = "./RoomTempEpoxyYHNew.txt";
		MatrixConductivity = 1e-15;
		/****
		java 6 does not support string case of switch-case, you can use if-else if more polymer 
		is added, and so does particle and graft
		
		switch(polymer) {
			// case "epoxy":
			default:
				PScoeff = "./RoomTempEpoxyYHNew.txt";
				MatrixConductivity = 1e-15;
		}
		*****/
		BufferedReader particlefile = new BufferedReader(new FileReader("./particle.word"));
		String particle = particlefile.readLine();
		
		
		FillerConductivity = 1e-15;
		FillerRelPerm = 3.9;
		/***
		switch(particle) {
			// case "SiO2":
			default:
				FillerConductivity = 1e-15;
				FillerRelPerm = 3.9;
		}
		****/
		
		BufferedReader graftfile = new BufferedReader(new FileReader("./graft.word"));
		String graft = graftfile.readLine();
		if (graft == "monoPGMA") {
				IF1mode = 0;
					
				InterfaceRelPerm = 0;
				InterfaceImagPerm = 0;
				InterfaceConductivity = 0;
				
				consttTauShift1 = 1;
				consttDeltaEpsilonShift1 = 1;
				consttTauShift2 = 1;
				consttDeltaEpsilonShift2 = 1;
				consttConstEpsilonShift = 0;
				binsource = "./crop_PGMA_2wt%_3D_structure_output.txt";
				consttdimension_to_pixel = 1.6667; // 400/240;
				consttdimension_to_pixel = 0.4630; // 200/432;
		} else if (graft == "ferroPGMA") {
				IF1mode = 1;
				InterfaceRelPerm = 4;
				InterfaceImagPerm = 0.24;
				InterfaceConductivity = 1e-12;
				consttTauShift1 = 1;
				consttDeltaEpsilonShift1 = 1;
				consttTauShift2 = 1;
				consttDeltaEpsilonShift2 = 1;
				consttConstEpsilonShift = 0;
				binsource = "./crop_ferroPGMA_2wt%_2_3D_structure_output.txt";
				consttdimension_to_pixel = 0.4630; // 200/432;
		} else if (graft == "terthiophenePGMA") {
				IF1mode = 1;
				InterfaceRelPerm = 2.4;
				InterfaceImagPerm = 0.001;
				InterfaceConductivity = 1e-11;
				consttTauShift1 = 1;
				consttDeltaEpsilonShift1 = 1;
				consttTauShift2 = 1;
				consttDeltaEpsilonShift2 = 1;
				consttConstEpsilonShift = 0;
				binsource = "./crop_terthiophenePGMA_2wt%_3D_structure_output.txt";
				consttdimension_to_pixel = 0.9560; // 500/ 523;
		} else if (graft == "monothiophenePGMA") {
				IF1mode = 1;
				InterfaceRelPerm = 2.4;
				InterfaceImagPerm = 0.001;
				InterfaceConductivity = 1e-11;
				consttTauShift1 = 1;
				consttDeltaEpsilonShift1 = 1;
				consttTauShift2 = 1;
				consttDeltaEpsilonShift2 = 1;
				consttConstEpsilonShift = 0;
				binsource = "./crop_monothiophenePGMA_2wt%_3D_structure_output.txt";
				consttdimension_to_pixel = 0.9560; // 500/523;
		} else if (graft == "anthraenePGMA") {
				IF1mode = 1;
				InterfaceRelPerm = 3.1;
				InterfaceImagPerm = 0.05;
				InterfaceConductivity = 1e-15;
				consttTauShift1 = 1;
				consttDeltaEpsilonShift1 = 1;
				consttTauShift2 = 1;
				consttDeltaEpsilonShift2 = 1;
				consttConstEpsilonShift = 0;
				binsource = "./crop_anthracenePGMA_2wt%_3D_structure_output.txt";
				consttdimension_to_pixel = 1.6667; // 400/240;			
		} else { // "bare"
				IF1mode = 0;
				
				InterfaceRelPerm = 0;
				InterfaceImagPerm = 0;
				InterfaceConductivity = 0;
				
				consttTauShift1 = 1;
				consttDeltaEpsilonShift1 = 1;
				consttTauShift2 = 1;
				consttDeltaEpsilonShift2 = 1;
				consttConstEpsilonShift = 0;
				binsource = "./crop_BS_1wt%_3D_structure_output.txt";
				consttdimension_to_pixel = 0.9560; // 500/523;			
		}
			
		BufferedReader vffile = new BufferedReader(new FileReader("./vf.word"));
		String vf = vffile.readLine();
		consttvf = Double.parseDouble(vf);
		
		BufferedReader ipthickfile = new BufferedReader(new FileReader("./ipthick.word"));
		String ipthick = ipthickfile.readLine();
		double IP2 = Double.parseDouble(ipthick);
		
		constttau = 0.1;
		/************************************************/
		
		ImageType = "recon";
		ControlMode = "linux";
		dimension_to_pixel = consttdimension_to_pixel;
		CutSide = 0.1;
		double IP1 = 5;
		// double IP2 = 10;
		InterfaceThickness1 = IP1 * 1e-6;
		InterfaceThickness2 = IP2 * 1e-6;
		
		isReScale = 0;
		isGetSolution = 1;
		isEpsDistribution = 1;
		ACMode = 1;
		ACbreakdown = 0;
		CheckOverlap = 0;
		ManualMesh = 0;
		MeshLevel = 5;
		InterfaceThickness = InterfaceThickness1 + InterfaceThickness2;
		
		String strACMode;
		if (ACMode == 1) {
			strACMode = "AC";
		} else if ( ACMode == 0) {
			strACMode = "DC";
		} else {
			strACMode = null;
		}
		
		DomLength = consttL;
		vf_expt       = consttvf;                        
		TauShift1     = consttTauShift1;  				
		DeltaEpsilonShift1   = consttDeltaEpsilonShift1 ;       
		TauShift2   = consttTauShift2;                
		DeltaEpsilonShift2   = consttDeltaEpsilonShift2;       
		ConstEpsilonShift   = consttConstEpsilonShift;
		tau0   = constttau;

		// String PScoeff = "./RoomTempEpoxyYHNew.txt";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
		Date date = Calendar.getInstance().getTime(); 
		
		
		String savefile = "./HZ_3D_comsolbuild_"+strACMode+"_"+ImageType+"_"+dateFormat.format(date)+"_IP"+Double.toString(IP1)+"+"+Double.toString(IP2)+"_run_"+Double.toString(TrialNo);


		
			
			/*****Model model = ModelUtil.load("model","base.mph");**************/
			Model model = ModelUtil.create("Model");

			// model.modelPath("/home/zyz293/javacode/3D_java_code_v5");

			model.modelNode().create("mod1");

			model.geom().create("geom1", 3);

			model.mesh().create("mesh1", "geom1");

			model.physics().create("ec", "ConductiveMedia", "geom1");

			model.geom("geom1").feature().create("blk1", "Block");
			model.geom("geom1").run("blk1");
			model.geom("geom1").run();

			model.material().create("mat1");
		        model.material("mat1").propertyGroup("def").set("electricconductivity", new String[]{"1E-12"});
		        model.material("mat1").propertyGroup("def").set("relpermittivity", new String[]{"4"});

		        model.physics("ec").feature("init1").set("V", 1, "1E-5");
		        model.physics("ec").feature().create("gnd1", "Ground", 2);
		        model.physics("ec").feature("gnd1").selection().set(new int[]{4});
		        model.physics("ec").feature().create("pc1", "PeriodicCondition", 2);
		        model.physics("ec").feature("pc1").selection().set(new int[]{1, 6});
		        model.physics("ec").feature().create("term1", "Terminal", 2);
		        model.physics("ec").feature("term1").selection().set(new int[]{3});
		        model.physics("ec").feature("term1").set("TerminalType", 1, "Voltage");
		        model.physics("ec").feature("term1").set("V0", 1, "0.1");

		        model.material().remove("mat1");

			/********************************************************************/		

			
			/**************model.myfun_comsol_load_constant();*******************/
			extra_E_infi         = -0.55;                
			AppliedVoltage    = 1.5e-3;               
			InitialVoltage       = 0.01e-3;              
			CurrentSource     = 2e3;    
			FreqRange = new double[46];
			for (int i = 0; i <46; i++) {
				FreqRange[i] = Math.pow(10.0, -3+i*0.2);
			}     
			TimeRange           = "range(0,0.25,20)";  
			
			// MatrixConductivity        = 1e-15;      
			// FillerConductivity          = MatrixConductivity;    
			// FillerRelPerm                 = 3.9;         
			
			
/* Move variables below to the beginning of file as material selection, polymer, particle, graft, vf, ipthick */			
			// InterfaceRelPerm          = 2.4;                
			// InterfaceImagPerm       = 0.001; 
			// InterfaceConductivity    = 1e-11;   



			
			ElectrodeRelPerm          = 2; 
			ElectrodeConductivity   = 1e6;   
			/*************************************************************/
			
			if (isEpsDistribution == 1) { 
				if (ACMode == 1) {
					System.out.println("AC mode is selected. Begin applying dielectric permittivity spectroscopy Prony series to polymer data.");
					
					
					BufferedReader Te = new BufferedReader(new FileReader(PScoeff));
					int row1 = Integer.parseInt(Te.readLine());
					int col1 = Integer.parseInt(Te.readLine());
					double[][] TemPR = new double[row1][col1];
					String line1 = Te.readLine();
					int m = 0;
					String[] str1;
					while (line1 != null) {
						str1 = line1.split(",");
						for (int j = 0; j <str1.length; j++) {
							TemPR[m][j] = Double.parseDouble(str1[j]);
						}
						m ++;
						line1 = Te.readLine();
					}	
					
					// matreader a = new matreader(PScoeff);
					// double[][] TemPR = a.TemPR;
					int NumOfTerms = TemPR.length;
					
					double[] MTD_Coeff = new double[2* NumOfTerms-1];
					for (int i = 0; i < NumOfTerms-1; i++) {
						MTD_Coeff[2*i] = TemPR[i][1];
						MTD_Coeff[2*i+1] = TemPR[i][0];
					}
					MTD_Coeff[2*NumOfTerms-2] = TemPR[NumOfTerms-1][1] + extra_E_infi;
					NumOfTerms = MTD_Coeff.length / 2;
					NumOfTerms = (int) NumOfTerms;

					String str_real = "";
					String str_imag = "";
					String str_Intreal = "";
					String str_Intimag = "";
					String str_CurrentTermImag = "";
					String str_CurrentTermReal = "";
					String str_CurrentIntTermImag = "";
					String str_CurrentIntTermReal = "";

					for (int i = 0; i < NumOfTerms; i++) {
						str_CurrentTermReal = String.format("+%.3f/(1+(10^(%.3f)*ec.freq/1[Hz])^2)", MTD_Coeff[2*i], MTD_Coeff[2*i+1]);
						str_CurrentTermImag = String.format("+%.3f*(10^(%.3f)*ec.freq/1[Hz])/(1+(10^(%.3f)*ec.freq/1[Hz])^2)", MTD_Coeff[2*i], MTD_Coeff[2*i+1], MTD_Coeff[2*i+1]);
						
						if (Math.pow(10.0, MTD_Coeff[2*i+1]) > tau0) {
							str_CurrentIntTermReal = String.format("+%.3f*DS2/(1+(10^(%.3f)*TS2*ec.freq/1[Hz])^2)", MTD_Coeff[2*i], MTD_Coeff[2*i+1]);
							str_CurrentIntTermImag = String.format("+%.3f*DS2*(10^(%.3f)*TS2*ec.freq/1[Hz])/(1+(10^(%.3f)*TS2*ec.freq/1[Hz])^2)", MTD_Coeff[2*i], MTD_Coeff[2*i+1], MTD_Coeff[2*i+1]);	
						} else {
							str_CurrentIntTermReal = String.format("+%.3f*DS1/(1+(10^(%.3f)*TS1*ec.freq/1[Hz])^2)", MTD_Coeff[2*i], MTD_Coeff[2*i+1]);
							str_CurrentIntTermImag = String.format("+%.3f*DS1*(10^(%.3f)*TS1*ec.freq/1[Hz])/(1+(10^(%.3f)*TS1*ec.freq/1[Hz])^2)", MTD_Coeff[2*i], MTD_Coeff[2*i+1], MTD_Coeff[2*i+1]);
						}
						
						str_real = str_real + str_CurrentTermReal;
						str_imag = str_imag + str_CurrentTermImag;
						str_Intreal = str_Intreal + str_CurrentIntTermReal;
						str_Intimag = str_Intimag + str_CurrentIntTermImag;
					}
					
					String rreal = String.format("+%.3f", MTD_Coeff[MTD_Coeff.length -1]);
					String immag = String.format("+%.3f+const", MTD_Coeff[MTD_Coeff.length - 1]);
					
					str_real = str_real + rreal;
					str_imag = str_imag + immag;
					
					ep = str_real;
					epp = str_imag;
					epint = str_Intimag;
					eppint = str_Intreal;

					/********************************************************************/
				} else {
					System.out.println("DC mode is selected. No need to compute complex dielectric permittivity.");
					ep = null;
					epp = null;
					epint = null;
					eppint = null;
				}
				/*************************************************************************/
			}


			/***************model.myfun_comsol_load_image(imagefile);************************/
			double RemainSide = 1-2*CutSide;
			System.out.println("Ratio of physical length to pixel: "+Double.toString(dimension_to_pixel)+"nm-per-pixel");
			dimensionX = DomLength*dimension_to_pixel*1e-6;
			dimensionY = dimensionX;
			dimensionZ = dimensionX;
			
			if (ImageType == "recon") {
				
				
				BufferedReader Im = new BufferedReader(new FileReader(binsource));
				int row2 = Integer.parseInt(Im.readLine());
				int col2 = Integer.parseInt(Im.readLine());
				double[][] img_para = new double[row2][col2];
				String line2 = Im.readLine();
				int n = 0;
				String[] str2;
				while (line2 != null) {
					str2 = line2.split(",");
					for (int j = 0; j <str2.length; j++) {
						img_para[n][j] = Double.parseDouble(str2[j]);
					}
					n ++;
					line2 = Im.readLine();
				}	
				
				
				
				
				// matreader b = new matreader(binsource);
				// double[][] img_para = b.img_para;
				NewClusterNo = (int) img_para.length;
				EllipseMatrix = new double[NewClusterNo][7];
				for (int i = 0; i < NewClusterNo; i++) {
					EllipseMatrix[i][0] = img_para[i][0]/DomLength*RemainSide*dimensionX + CutSide*dimensionX;
					EllipseMatrix[i][1] = img_para[i][1]/DomLength*RemainSide*dimensionY + CutSide*dimensionY;
					EllipseMatrix[i][2] = img_para[i][2]/DomLength*RemainSide*dimensionZ + CutSide*dimensionZ;
					EllipseMatrix[i][3] = img_para[i][3]/DomLength*RemainSide*dimensionX;
					EllipseMatrix[i][4] = img_para[i][4]/DomLength*RemainSide*dimensionX;
					EllipseMatrix[i][5] = img_para[i][5];
					EllipseMatrix[i][6] = img_para[i][6];
				}
				System.out.println("Number of clusters in FEA geometry: "+Integer.toString(NewClusterNo));
				double[] ShortSq = new double[NewClusterNo];
				for (int i = 0; i < NewClusterNo; i++) {
					ShortSq[i] = Math.pow(EllipseMatrix[i][4], 2);
				}
				
				double ActualVF = 4.0/3.0*3.1416/(dimensionX*dimensionY*dimensionZ);
				double matrix1 = 0;
				for (int i = 0; i< NewClusterNo; i++) {
					matrix1 = matrix1 + EllipseMatrix[i][3]*ShortSq[i];
				}
				ActualVF = ActualVF * matrix1;
				
				System.out.println("VF before scaling: "+Double.toString(ActualVF));
				
				if (isReScale == 1) {
					ReScale = Math.pow(vf_expt/ActualVF, 1/3);
					for (int i = 0; i < NewClusterNo; i++) {
						EllipseMatrix[i][3] = EllipseMatrix[i][3]*ReScale;
						EllipseMatrix[i][4] = EllipseMatrix[i][4]*ReScale;
					}
				}
				
				for (int i = 0; i < NewClusterNo; i++) {
					ShortSq[i] = Math.pow(EllipseMatrix[i][4], 2);
				}
				
				double CorrectedVF = 4/3*3.1416/(dimensionX*dimensionY*dimensionZ);
				double matrix2 = 0;
				for (int i = 0; i< NewClusterNo; i++) {
					matrix2 = matrix2 + EllipseMatrix[i][3]*ShortSq[i];
				}
				CorrectedVF = CorrectedVF * matrix2;
				System.out.println("Corrected VF in simulation window: "+Double.toString(CorrectedVF));
			}
			
			/**************************************************************************/

			/******************model.myfun_comsol_create_model();**************************/
			model.geom("geom1").lengthUnit("mm");
			model.variable().create("var1");
			model.variable("var1").model("mod1");

			if (ACMode == 1) {
				System.out.println("AC mode: define polymer and interphase dielectric permittivity.");
				model.variable("var1").set("ep",ep);
				model.variable("var1").set("epp",epp);
				model.variable("var1").set("epint",epint);
				model.variable("var1").set("eppint",eppint);       
			}
			
			/**************************************************************************/

			/******************model.myfun_comsol_create_structure();************************/
			System.out.println("Load cluster geometry...");
			String[] FeatureName = new String[NewClusterNo];
			String[] FeatureName1 = new String[NewClusterNo];
			String[] FeatureName2 = new String[NewClusterNo];
			
			model.geom("geom1").feature("blk1").set("size", new double[] {dimensionX, dimensionY, dimensionZ});
			model.geom("geom1").feature("blk1").set("createselection", "on");
			model.geom("geom1").runAll();

			for (int i = 0; i < NewClusterNo; i++) {
				FeatureName[i] = "Ellipse"+Integer.toString(i);
				model.geom("geom1").feature().create(FeatureName[i],"Ellipsoid");	
				model.geom("geom1").feature(FeatureName[i]).set("pos", new double[]{EllipseMatrix[i][0], EllipseMatrix[i][1], EllipseMatrix[i][2]});
				model.geom("geom1").feature(FeatureName[i]).set("semiaxes", new double[]{EllipseMatrix[i][3], EllipseMatrix[i][4], EllipseMatrix[i][4]});
				model.geom("geom1").feature(FeatureName[i]).set("rot", EllipseMatrix[i][5]);
				model.geom("geom1").feature(FeatureName[i]).set("createselection","on");
				System.out.println("Ellipse"+Integer.toString(i));
			}
			model.geom("geom1").runAll();
			model.geom("geom1").feature().create("UnionFiller", "Union");
			model.geom("geom1").feature("UnionFiller").selection("input").set(FeatureName);
			model.geom("geom1").feature("UnionFiller").set("createselection", "on");
			model.geom("geom1").feature("UnionFiller").set("intbnd", "off");
			model.geom("geom1").runAll();

			for (int i = 0; i < NewClusterNo; i ++) {
				FeatureName1[i] = "EllipseIF1"+Integer.toString(i);
				model.geom("geom1").feature().create(FeatureName1[i],"Ellipsoid");
				model.geom("geom1").feature(FeatureName1[i]).set("pos", new double[]{EllipseMatrix[i][0], EllipseMatrix[i][1], EllipseMatrix[i][2]});
				model.geom("geom1").feature(FeatureName1[i]).set("semiaxes", new double[]{EllipseMatrix[i][3]+InterfaceThickness1, EllipseMatrix[i][4]+InterfaceThickness1});
				model.geom("geom1").feature(FeatureName1[i]).set("rot", EllipseMatrix[i][5]);
				model.geom("geom1").feature(FeatureName1[i]).set("createselection","on");
				System.out.println("EllipseIF1-"+Integer.toString(i));
			}
			model.geom("geom1").runAll();
			model.geom("geom1").feature().create("UnionLargeEllipse1", "Union");
			model.geom("geom1").feature("UnionLargeEllipse1").selection("input").set(FeatureName1);
			model.geom("geom1").feature("UnionLargeEllipse1").set("createselection", "on");
			model.geom("geom1").feature("UnionLargeEllipse1").set("intbnd", "off");
			// mphsave(model,"test_mid1");
			model.name("test_mid1.mph");			
			model.geom("geom1").runAll();
			
			model.geom("geom1").feature().create("DiffInterface1", "Difference");
			model.geom("geom1").feature("DiffInterface1").selection("input").set("UnionLargeEllipse1");
			model.geom("geom1").feature("DiffInterface1").selection("input2").set("UnionFiller");
			model.geom("geom1").feature("DiffInterface1").set("keep", "on");
			model.geom("geom1").feature("DiffInterface1").set("createselection", "on");
			model.geom("geom1").feature("DiffInterface1").set("intbnd", "off");
			model.geom("geom1").runAll();

			for (int i = 1; i <= NewClusterNo; i++) {
				FeatureName2[i] = "EllipseIF2"+Integer.toString(i);
				model.geom("geom1").feature().create(FeatureName2[i],"Ellipse");
				model.geom("geom1").feature(FeatureName2[i]).set("pos", new double[]{EllipseMatrix[i][0], EllipseMatrix[i][1], EllipseMatrix[i][2]});
				model.geom("geom1").feature(FeatureName2[i]).set("semiaxes", new double[]{EllipseMatrix[i][3]+InterfaceThickness1+InterfaceThickness2, EllipseMatrix[i][4]+InterfaceThickness1+InterfaceThickness2, EllipseMatrix[i][4]+InterfaceThickness1+InterfaceThickness2});
				model.geom("geom1").feature(FeatureName2[i]).set("rot", EllipseMatrix[i][5]);
				model.geom("geom1").feature(FeatureName2[i]).set("createselection","on");
				System.out.println("EllipseIF2-"+Integer.toString(i));
			}
			model.geom("geom1").runAll();
			model.geom("geom1").feature().create("UnionLargeEllipse2", "Union");
			model.geom("geom1").feature("UnionLargeEllipse2").selection("input").set(FeatureName2);
			model.geom("geom1").feature("UnionLargeEllipse2").set("createselection", "on");
			model.geom("geom1").feature("UnionLargeEllipse2").set("intbnd", "off");
			model.geom("geom1").runAll();
			
			model.geom("geom1").feature().create("DiffInterface2", "Difference");
			model.geom("geom1").feature("DiffInterface2").selection("input").set(new String[] {"UnionLargeEllipse2"});
			model.geom("geom1").feature("DiffInterface2").selection("input2").set(new String[] {"UnionLargeEllipse1"});
			model.geom("geom1").feature("DiffInterface2").set("keep", "on");
			model.geom("geom1").feature("DiffInterface2").set("createselection", "on");
			model.geom("geom1").feature("DiffInterface2").set("intbnd", "off");
			model.geom("geom1").runAll();
			
					
			model.geom("geom1").feature().create("DiffMatrix", "Difference");
			model.geom("geom1").feature("DiffMatrix").selection("input").set(new String[] {"blk1"});
			model.geom("geom1").feature("DiffMatrix").selection("input2").set(new String[] {"UnionLargeEllipse2"});
			model.geom("geom1").feature("DiffMatrix").set("keep", "on");
			model.geom("geom1").feature("DiffMatrix").set("createselection", "on");
			model.geom("geom1").runAll();
			
			System.out.println("Finished building unions and differences on fillers, interphase, and rectangular simulation block.");
			
			/***************************************************************************/


			/***********************model.myfun_comsol_create_material();*********************/
			model.material().create("mat1");
			model.material("mat1").selection().named("geom1_UnionFiller_dom");
			model.material("mat1").propertyGroup("def").set("electricconductivity", FillerConductivity);
			model.material("mat1").propertyGroup("def").set("relpermittivity",FillerRelPerm);
			
			if (ACMode == 1) {
				model.material().create("mat2"); 
				model.material("mat2").selection().named("geom1_DiffMatrix_dom");
				model.material().create("mat4"); 
				model.material("mat4").selection().named("geom1_DiffInterface1_dom");
				model.material().create("mat5");
				model.material("mat5").selection().named("geom1_DiffInterface2_dom");
				
				if (ACbreakdown == 1) {
					model.material("mat4").propertyGroup("def").set("relpermittivity", "ep-j*epp-3.9*MyBoola");
					model.material("mat2").propertyGroup("def").set("relpermittivity", "ep-j*epp-MyBoolint*3.9"); 
					model.material("mat5").propertyGroup("def").set("relpermittivity", "ep-j*epp-3.9*MyBoola");
					model.material("mat2").propertyGroup("def").set("electricconductivity", "MyBoola*1e2+1e-4");
					model.material("mat4").propertyGroup("def").set("electricconductivity", "MyBoolint*1e2+1e-4"); 
					model.material("mat5").propertyGroup("def").set("electricconductivity", "MyBoolint*1e2+1e-4");  
				} else {
					model.material("mat2").propertyGroup("def").set("relpermittivity", "ep-j*epp");
					model.material("mat2").propertyGroup("def").set("electricconductivity", MatrixConductivity);    
					
					
 			if (IF1mode == 1) {
                String strInterfacePerm = Double.toString(InterfaceRelPerm)+"-j*"+ Double.toString(InterfaceImagPerm);
                model.material("mat4").propertyGroup("def").set("relpermittivity", strInterfacePerm);
                model.material("mat4").propertyGroup("def").set("electricconductivity", InterfaceConductivity);
			} else if (IF1mode == 0) {               
                model.material("mat4").propertyGroup("def").set("relpermittivity", new String[] {"epint-j*eppint"});
                model.material("mat4").propertyGroup("def").set("electricconductivity", MatrixConductivity); 
			}
					
					
					
					/* Change below lines to if and elseif above 
					String strInterfacePerm = Double.toString(InterfaceRelPerm)+"-j*"+ Double.toString(InterfaceImagPerm);
					model.material("mat4").propertyGroup("def").set("relpermittivity", strInterfacePerm);   
					model.material("mat4").propertyGroup("def").set("electricconductivity", InterfaceConductivity);   
					*/
					
					
					
					
                    
					model.material("mat5").propertyGroup("def").set("relpermittivity", "epint-j*eppint");
					model.material("mat5").propertyGroup("def").set("electricconductivity", MatrixConductivity);    
				}
			} else {
				model.material().create("mat2");
				model.material("mat2").selection().named("geom1_DiffMatrix_dom");
				model.material("mat2").propertyGroup("def").set("electricconductivity", "MyBoola*1e2+1e-4");
				model.material("mat2").propertyGroup("def").set("relpermittivity", "5-3.9*MyBoola");
			
				model.material().create("mat3");
				model.material("mat3").selection().named("geom1_poly1_dom");
				model.material("mat3").propertyGroup("def").set("electricconductivity", ElectrodeConductivity);
				model.material("mat3").propertyGroup("def").set("relpermittivity",ElectrodeRelPerm);
			
				model.material().create("mat4");
				model.material("mat4").selection().named("geom1_DiffInterface1_dom");
				model.material("mat4").propertyGroup("def").set("electricconductivity", "MyBoolint*1e2+1e-4");
				model.material("mat4").propertyGroup("def").set("relpermittivity", "5-MyBoolint*3.9");
				model.material().create("mat5");
				model.material("mat5").selection().named("geom1_DiffInterface2_dom");
				model.material("mat5").propertyGroup("def").set("electricconductivity", "MyBoolint*1e2+1e-4");
				model.material("mat5").propertyGroup("def").set("relpermittivity", "5-MyBoolint*3.9");
			}

			System.out.println("Created material.");
			/***************************************************************************/
		
			/*******************model.myfun_comsol_create_physics();**************************/
			if (ACMode == 1) {
				model.physics("ec").feature("init1").set("V", 1, InitialVoltage);
				model.physics("ec").feature("term1").set("V0", 1, AppliedVoltage);
				System.out.println("created EC physics");
			}
			
			
			/***************************************************************************/

			/*******************model.myfun_comsol_create_mesh();*************************/
			if (ManualMesh == 1) { // it should be a num
				double hmax = 0.08;
				double hmin = 0.01;
				double hgrad = 1.5;
				double hcurve = 5;
				double hnarrow = 0.2;
				
				model.mesh("mesh1").automatic(false);      
				model.mesh("mesh1").feature("size").set("custom", "on");   
				model.mesh("mesh1").feature("size").set("hmax", hmax);
				model.mesh("mesh1").feature("size").set("hmin", hmin);
				model.mesh("mesh1").feature("size").set("hgrad", hgrad);
				model.mesh("mesh1").feature("size").set("hcurve", hcurve);
				model.mesh("mesh1").feature("size").set("hnarrow", hnarrow);
			} else {
				model.mesh("mesh1").autoMeshSize(MeshLevel); 
			}
			model.mesh("mesh1").run();
			System.out.println("Finished meshing");		
			
			/*************************************************************************/

			if (ACMode == 1) {
				double[] SF = new double[5];
				SF[0] = TauShift1;
				SF[1] = DeltaEpsilonShift1;
				SF[2] = TauShift2;
				SF[3] = DeltaEpsilonShift2;
				SF[4] = ConstEpsilonShift;
				
				/*************model.myfun_comsol_create_shifting_factors(SF);*******************/
				String str_TauShift1 = Double.toString(SF[0]);
				String str_DeltaEpsilonShift1      = Double.toString(SF[1]);
				String str_TauShift2               = Double.toString(SF[2]);
				String str_DeltaEpsilonShift2      = Double.toString(SF[3]);
				String str_ConstantC               = Double.toString(SF[4]);
				
				model.variable("var1").set("TS1",str_TauShift1); 
				model.variable("var1").set("DS1",str_DeltaEpsilonShift1);
				model.variable("var1").set("TS2",str_TauShift2); 
				model.variable("var1").set("DS2",str_DeltaEpsilonShift2);
				model.variable("var1").set("const",str_ConstantC);			
				
				
				/************************************************************************/
			}

			/******************model.myfun_comsol_create_study();*************************/
			if (ACbreakdown == 1) {
				model.study().create("std1");
				model.study("std1").feature().create("freq", "Frequency");
				model.study("std1").feature("freq").set("geomselection", "geom1");
				model.study("std1").feature("freq").set("physselection", "ec");
				model.study("std1").feature("freq").set("plist", FreqRange);
				
				if (ACbreakdown == 1) {
					model.study().create("std2");
					model.study("std2").feature().create("time", "Transient");
					model.study("std2").feature("time").set("geomselection", "geom1");
					model.study("std2").feature("time").set("physselection", "dode3");
					model.study("std2").feature("time").set("probefreq", "tout");
					model.study("std2").feature("time").set("tlist", TimeRange);
				}
			} else {
				model.study().create("std1");
				model.study("std1").feature().create("time", "Transient");
				model.study("std1").feature("time").set("geomselection", "geom1");
				model.study("std1").feature("time").set("physselection", "dode3");
				model.study("std1").feature("time").set("probefreq", "tout");
				model.study("std1").feature("time").set("tlist", TimeRange);
			}
			System.out.println("created study.");		
			
			/*************************************************************************/

			if (isGetSolution == 1) {
				/***************model.myfun_comsol_create_solution();*************************/
				if (ACMode == 1) {
					model.sol().create("sol1");
					model.sol("sol1").study("std1");
					model.sol("sol1").feature().create("st1", "StudyStep");
					model.sol("sol1").feature("st1").set("study", "std1");
					model.sol("sol1").feature("st1").set("studystep", "freq");
					model.sol("sol1").feature().create("v1", "Variables");
					model.sol("sol1").feature().create("s1", "Stationary");
					model.sol("sol1").feature("s1").feature().create("p1", "Parametric");
					model.sol("sol1").feature("s1").feature().remove("pDef");
					model.sol("sol1").feature("s1").feature("p1").set("pname", "freq");
					model.sol("sol1").feature("s1").feature("p1").set("plist", FreqRange);
					model.sol("sol1").feature("s1").feature("p1").set("plot", "off");
					model.sol("sol1").feature("s1").feature("p1").set("probesel", "all");
					model.sol("sol1").feature("s1").feature("p1").set("probes", new String[] {});
					model.sol("sol1").feature("s1").feature("p1").set("control", "freq");
					model.sol("sol1").feature("s1").set("control", "freq");
					model.sol("sol1").feature("s1").feature().create("fc1", "FullyCoupled");
					model.sol("sol1").feature("s1").feature().create("i1", "Iterative");
					model.sol("sol1").feature("s1").feature("i1").set("linsolver", "bicgstab");
					model.sol("sol1").feature("s1").feature("fc1").set("linsolver", "i1");
					model.sol("sol1").feature("s1").feature("i1").feature().create("mg1", "Multigrid");
					model.sol("sol1").feature("s1").feature("i1").feature("mg1").set("prefun", "gmg");
					model.sol("sol1").feature("s1").feature().remove("fcDef");
					model.sol("sol1").attach("std1");
					model.sol("sol1").runAll();
				} else {
					 model.sol().create("sol1");
					model.sol("sol1").study("std1");
					model.sol("sol1").feature().create("st1", "StudyStep");
					model.sol("sol1").feature("st1").set("study", "std1");
					model.sol("sol1").feature("st1").set("studystep", "time");
					model.sol("sol1").feature().create("v1", "Variables");
					model.sol("sol1").feature().create("t1", "Time");
					model.sol("sol1").feature("t1").set("tlist", TimeRange);
					model.sol("sol1").feature("t1").set("plot", "off");
					model.sol("sol1").feature("t1").set("plotfreq", "tout");
					model.sol("sol1").feature("t1").set("probesel", "all");
					model.sol("sol1").feature("t1").set("probes", new String[] {});
					model.sol("sol1").feature("t1").set("probefreq", "tsteps");
					model.sol("sol1").feature("t1").set("control", "time");
					model.sol("sol1").feature("t1").feature().create("fc1", "FullyCoupled");
					model.sol("sol1").feature("t1").feature().remove("fcDef");
					model.sol("sol1").attach("std1");
					model.sol("sol1").runAll();
				}
				
				System.out.println("Finished computing solution.");			
				
				/************************************************************************/
				
				/**********************model.myfun_comsol_post_process(savefile);**************/
				if (ACMode == 1) {
					model.result().create("pg1", 3);
					model.result("pg1").set("data", "dset1");
					model.result("pg1").feature().create("slc1", "Slice");
					model.result("pg1").feature("slc1").set("expr", "V");
					model.result("pg1").feature("slc1").set("quickplane", "xy");
					model.result("pg1").feature("slc1").set("quickznumber", "1");
					model.result("pg1").feature().create("slc2", "Slice");
					model.result("pg1").feature("slc2").set("expr", "V");
					model.result("pg1").feature("slc2").set("quickplane", "yz");
					model.result("pg1").feature("slc2").set("quickxnumber", "1");
					model.result("pg1").feature("slc2").set("inheritplot", "slc1");
					model.result("pg1").feature().create("slc3", "Slice");
					model.result("pg1").feature("slc3").set("expr", "V");
					model.result("pg1").feature("slc3").set("quickplane", "zx");
					model.result("pg1").feature("slc3").set("quickynumber", "1");
					model.result("pg1").feature("slc3").set("inheritplot", "slc1");
					model.result("pg1").name("Electric Potential (ec)");
					model.result("pg1").run();
					
					String str_EpsilonBulkImag = String.format("imag(ec.Jy/%.3f[V])*%.3f[mm]/ec.freq/2/3.14/8.85e-12[F/m]", AppliedVoltage, dimensionY);
					
					model.result().numerical().create("av1", "AvSurface");
					model.result().numerical("av1").selection().set(new int[] {4});
					model.result().table().create("tbl1", "Table");
					model.result().table("tbl1").comments("Surf Average 1, Epsilon Double Prime");
					model.result().numerical("av1").set("table", "tbl1");
					model.result().numerical("av1").set("expr", str_EpsilonBulkImag);
					model.result().numerical("av1").set("descr", "Epsilon Double Prime");
					model.result().numerical("av1").setResult();
					model.result().create("pg2", 1);
					model.result("pg2").feature().create("tblp1", "Table");
					model.result("pg2").feature("tblp1").set("table", "tbl1");
					model.result("pg2").run();
					model.result().export().create("plot1", "pg2", "tblp1", "Plot");
					model.result().export("plot1").set("header", "off");
					
					String txtfilenameImag = savefile + "_CompPermImag.csv";
					
					System.out.print("Write imaginary composite permittivity to file: ");
					System.out.println(txtfilenameImag);
					
					model.result().export("plot1").set("filename", txtfilenameImag);
					model.result().export("plot1").run();
					
					String str_EpsilonBulkReal = String.format("imag(ec.Jy/%.3f[V])*%.3f[mm]/ec.freq/2/3.14/8.85e-12[F/m]", AppliedVoltage, dimensionY);
				
					model.result().numerical().create("av2", "AvSurface");
					model.result().numerical("av2").selection().set(new int[] {4});
					model.result().table().create("tbl2", "Table");
					model.result().table("tbl2").comments("Surf Average 2, Epsilon Prime");
					model.result().numerical("av2").set("table", "tbl2");
					model.result().numerical("av2").set("expr", str_EpsilonBulkReal);
					model.result().numerical("av2").set("descr", "Epsilon Prime");
					model.result().numerical("av2").setResult();
					
					String txtfilenameReal = savefile + "_CompPermReal.csv";
					
					model.result().create("pg3", 1);
					model.result("pg3").feature().create("tblp2", "Table");
					model.result("pg3").feature("tblp2").set("table", "tbl2");
					model.result("pg3").run();
					model.result().export().create("plot2", "pg3", "tblp2", "Plot");
					model.result().export("plot2").set("header", "off");
					model.result().export("plot2").set("filename", txtfilenameReal);
					
					System.out.print("Write real composite permittivity to file: ");
					System.out.println(txtfilenameReal);
					
					model.result().export("plot2").run();
				} else {
					model.result().create("pg1", 2);
					model.result("pg1").set("data", "dset1");
					model.result("pg1").feature().create("surf1", "Surface");
					
					model.result("pg1").feature("surf1").set("expr", "V");
					model.result("pg1").name("Electric Potential (ec)");
					
					model.result().create("pg2", 2);
					model.result("pg2").set("data", "dset1");
					model.result("pg2").feature().create("surf1", "Surface");
					model.result("pg2").feature("surf1").set("expr", new String[]{"log(ec.sigmaxx)"});
					model.result("pg2").name("Electric Conductivity (ec)");
					
					model.result("pg1").run();
					model.result("pg2").run();
				}
				
				
				/************************************************************************/
			}

			model.name(savefile + ".mph");
			System.out.println("Job done. Output result to .mph file");
			
			File source = new File("./" + savefile + ".mph");
			source.renameTo(new File(wdir +"/" + source.getName()));
			
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
