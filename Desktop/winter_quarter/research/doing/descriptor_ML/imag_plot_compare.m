clear;clc;

data = csvread('C:\Users\DaTui\Desktop\sample9-20rpm-60kx_2D_comsolbuild_AC_binary_05-Feb-2015_IP10+50_run_1_CompPermImag.csv');
x = log(data(:,1));
y = data(:,2);
figure(1)
plot(x,y,'-r');
hold on

a1 = 0.00000001526143;
a2 = -0.0000003124087;
a3 = -0.0000007050768;
a4 = 0.00003541667;
a5 =-0.0001058866;
a6 = 0.0005033774;
a7 = 0.003135387;
a8 = 0.02281132;
yf = a8+a7*x+a6*x.^2+a5*x.^3+a4*x.^4+a3*x.^5+a2*x.^6+a1*x.^7;
plot(x,yf,'-b');
hold on;

% p1 = 1.3735e-08;  % linear **** (design) Kmeans     total descriptors version
% p2 = -2.6235e-07;
% p3 = -1.1775e-06;
% p4 = 3.543e-05;
% p5 = -8.8255e-05;
% p6 = 0.00044877;
% p7 = 0.0030219;
% p8 = 0.022791;
% yp1 = p8+p7*x+p6*x.^2+p5*x.^3+p4*x.^4+p3*x.^5+p2*x.^6+p1*x.^7;
% plot(x,yp1,'-g');
% hold on;

% p1 = 1.6015e-08;  % GRNN 0.01 Kmeans     total descriptors version
% p2 = -3.3185e-07;
% p3 = -6.4843e-07;
% p4 = 3.7386e-05;
% p5 = -0.00012083;
% p6 = 0.00050495;
% p7 = 0.0032127;
% p8 = 0.022697;
% yp2 = p8+p7*x+p6*x.^2+p5*x.^3+p4*x.^4+p3*x.^5+p2*x.^6+p1*x.^7;
% plot(x,yp2,'-b');
% hold on;

a1 = 1.9476e-08;   %  GRNN 0.01 Kmeans  leave_sk
a2 = -4.1507e-07;
a3 = -6.0263e-07;
a4 = 4.8911e-05;
a5 = -0.00019928;
a6 = 0.00051698;
a7 = 0.0036865;
a8 = 0.022316;
yp = a8+a7*x+a6*x.^2+a5*x.^3+a4*x.^4+a3*x.^5+a2*x.^6+a1*x.^7;
plot(x,yp,'-g');
hold on;
legend('simulation curve', 'fitting curve', 'prediction curve');
%a1 = 1.3027e-08;   %  linear *** (design) Kmeans  leave_sk
%a2 = -2.3886e-07;
%a3 = -1.4044e-06;
%a4 = 3.5536e-05;
%a5 = -8.0176e-05;
%a6 = 0.00041208;
%a7 = 0.0029388;
%a8 = 0.022664;
%yp = a8+a7*x+a6*x.^2+a5*x.^3+a4*x.^4+a3*x.^5+a2*x.^6+a1*x.^7;
%plot(x,yp,'-b');
%hold on;

%a1 = 8.3048e-09;   %  investigate 
%a2 = -9.4021e-08;
%a3 = -2.5302e-06;
%a4 = 3.2013e-05;
%a5 = -1.51e-05;
%a6 = 0.00026237   ;
%a7 = 0.0024217   ;
%a8 = 0.022388   ;
%yi = a8+a7*x+a6*x.^2+a5*x.^3+a4*x.^4+a3*x.^5+a2*x.^6+a1*x.^7;
%plot(x,yi,'--');
%hold on;