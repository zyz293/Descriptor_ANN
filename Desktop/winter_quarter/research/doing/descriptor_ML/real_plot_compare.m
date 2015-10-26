clear;clc;

data = csvread('C:\Users\DaTui\Desktop\sample9-20rpm-60kx_2D_comsolbuild_AC_binary_05-Feb-2015_IP10+50_run_1_CompPermReal.csv');
x = log(data(:,1));
y = data(:,2);
figure(1)
plot(x,y,'-r');
hold on

a1 = 4.20451;
a2 = 0.0127611;
a3 = 0.2749536;
a4 = -0.01447229;
a5 =-0.0009255385;
a6 = -0.0003364819;
yf = a1 + a2* exp(a3*x)+ a4*x + a5*x.^2 + a6*x.^3;
plot(x,yf,'-r');
hold on;

% a1 = 4.1982; % GRNN 0.01 Kmeans  total descriptors version
% a2 = 0.013812;
% a3 =  0.27178;
% a4 =  -0.014569;
% a5 =  -0.00089305;
% a6 = -0.00034413;
% yp1 = a1 + a2* exp(a3*x)+ a4*x + a5*x.^2 + a6*x.^3;
% plot(x,yp1,'-g');
% hold on

% a1 = 4.1828; % design Kmeans   total descriptors version
% a2 = 0.014965;
% a3 =  0.27129;
% a4 =  -0.014794;
% a5 =  -0.00098155;
% a6 = -0.00033702;
% yp1 = a1 + a2* exp(a3*x)+ a4*x + a5*x.^2 + a6*x.^3;
% plot(x,yp1,'-b');
% hold on

a1 = 4.1685; % GRNN 0.01 Kmeans leave_sk 
a2 = 0.036849;
a3 =  0.232;
a4 =  -0.018587;
a5 =  -0.0010868;
a6 = -0.00041665;
yp1 = a1 + a2* exp(a3*x)+ a4*x + a5*x.^2 + a6*x.^3;
plot(x,yp1,'-g');
hold on

a1 = 4.1552; % design Kmeans  leave_sk
a2 = 0.015216;
a3 =  0.27289;
a4 =  -0.014689;
a5 =  -0.00096891;
a6 = -0.000333;
yp1 = a1 + a2* exp(a3*x)+ a4*x + a5*x.^2 + a6*x.^3;
plot(x,yp1,'-b');
hold on

a1 = 4.0626 ;
a2 = 0.0019879  ;
a3 = 0.36128  ;
a4 = -0.011673  ;
a5 =-0.00081641  ;
a6 = -0.00023873  ;
yi = a1 + a2* exp(a3*x)+ a4*x + a5*x.^2 + a6*x.^3;
plot(x,yi,'--');
hold on;