function [StrokeTable_new]=Decimate(StrokeTable,N,d)
%Keeps every d th data point. StrokeTable is a N1*2*N2 table
%containing N1 characters of N strokes. N2=N*n, where n is the
%number of data points per stroke. With this function you can
%preprocess character tables 

n=size(StrokeTable,3)/N;
keep=floor(1+mod(n,d)/2):d:n;
for i=1:N-1
  keep=[keep,n*i+keep];
end
StrokeTable_new=StrokeTable(:,:,keep);
