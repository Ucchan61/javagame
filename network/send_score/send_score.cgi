#!/usr/bin/perl

# ���M���ꂽ�f�[�^���󂯎��
if ($ENV{'REQUEST_METHOD'} eq "POST") {
	read(STDIN, $buffer, $ENV{'CONTENT_LENGTH'});
}

# �f�[�^�𕪊�����
@pairs = split(/&/, $buffer);
# �e�f�[�^������ɕ�������%param�n�b�V���ɓ����
foreach $pair (@pairs) {
	($key, $value) = split(/=/, $pair);
	# value�����{��̏ꍇ�R�[�h������Ă���̂ŕϊ�����
	$value =~ s/%([a-fA-F0-9][a-fA-F-0-9])/pack("C", hex($1))/eg;
	$param{$key} = $value;
}

# �o�͂����HTML
print <<END;
Content-type: text/html

<html>
<head><title>���Ȃ��̓��_�͓o�^����܂���</title></head>
<body>
<h1>���Ȃ��̓��_�͓o�^����܂���</h1>
<p>$param{name}�����$param{score}�_�ł��B</p>
</body>
</html>
END
