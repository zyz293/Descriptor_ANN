clear;clc;
characterization = fopen('characterization_new.csv');
reference_name = textscan(characterization, '%s %*[^\n]');
for i = 1:195
    name = strsplit(reference_name{1,1}{i+1,1},',');
    reference_name{1,1}{i+1,1} = name{1};
end
fclose(characterization);

real_parameter = fopen('real_fit_parameter.csv');
real_name = textscan(real_parameter, '%s %*[^\n]');
for i = 1:196
    name_real = strsplit(real_name{1,1}{i+1,1},',');
    real_name{1,1}{i+1,1} = name_real{1};
end
fclose(real_parameter);
real_parameter_data = csvread('C:\Users\DaTui\Desktop\winter_quarter\research\doing\descriptor_ML\real_fit_parameter.csv',1,1);


imag_parameter = fopen('imag_fit_parameter_poly.csv');
imag_name = textscan(imag_parameter, '%s %*[^\n]');
for i = 1:196
    name_imag = strsplit(imag_name{1,1}{i+1,1},',');
    imag_name{1,1}{i+1,1} = name_imag{1};
end
fclose(imag_parameter);
imag_parameter_data = csvread('C:\Users\DaTui\Desktop\winter_quarter\research\doing\descriptor_ML\imag_fit_parameter_poly.csv',1,1);

fid_real = fopen('real_fit_parameter_new.csv', 'w');
fprintf(fid_real,'%s,','sample_name');
fprintf(fid_real,'%s,','a1');
fprintf(fid_real,'%s,','a2');
fprintf(fid_real,'%s,','a3');
fprintf(fid_real,'%s,','a4');
fprintf(fid_real,'%s,','a5');
fprintf(fid_real,'%s\n','a6');
fid_imag = fopen('imag_fit_parameter_new.csv', 'w');
fprintf(fid_imag,'%s,','sample_name');
fprintf(fid_imag,'%s,','a1');
fprintf(fid_imag,'%s,','a2');
fprintf(fid_imag,'%s,','a3');
fprintf(fid_imag,'%s,','a4');
fprintf(fid_imag,'%s,','a5');
fprintf(fid_imag,'%s,','a6');
fprintf(fid_imag,'%s,','a7');
fprintf(fid_imag,'%s\n','a8');

for i = 2:196
    for j = 2:197
         if strcmp(reference_name{1,1}{i,1}, real_name{1,1}{j,1}) == 1
            fprintf(fid_real, '%s,', real_name{1,1}{j,1});
            for  k = 1:5
                fprintf(fid_real, '%d,', real_parameter_data(i-1,k));
            end
            fprintf(fid_real, '%d\n', real_parameter_data(i-1,6));
         end
    end
end

for i = 2:196
    for j = 2:197
         if strcmp(reference_name{1,1}{i,1}, imag_name{1,1}{j,1}) == 1
            fprintf(fid_imag, '%s,', imag_name{1,1}{j,1});
            for  k = 1:7
                fprintf(fid_imag, '%d,', imag_parameter_data(i-1,k));
            end
            fprintf(fid_imag, '%d\n', imag_parameter_data(i-1,8));
         end
    end
end
fclose(fid_real);
fclose(fid_imag);