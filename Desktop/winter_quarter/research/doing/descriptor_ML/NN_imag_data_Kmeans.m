clear;clc;
data = csvread('C:/Users/DaTui/Desktop/winter_quarter/research/doing/descriptor_ML/chracterization_normalization_leave_s_k.csv');
data_input = data(1:192,[2,5,18])'; % total is [1,13,9,5,20], leave_sk is [2,5,18] also should change input file
data_test = data(193,[2,5,18])';  % should change too
parameter = csvread('C:/Users/DaTui/Desktop/winter_quarter/research/doing/descriptor_ML/imag_fit_parameter_poly_new.csv',1,1);
data_output = parameter(1:192,1:8)';
a = csvread('C:/Users/DaTui/Desktop/winter_quarter/research/doing/descriptor_ML/investigate_input_file.csv');
inves = a';